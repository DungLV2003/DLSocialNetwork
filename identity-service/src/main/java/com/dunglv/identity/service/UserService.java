package com.dunglv.identity.service;

import com.dunglv.event.dto.NotificationEvent;
import com.dunglv.identity.dto.request.UserCreationRequest;
import com.dunglv.identity.dto.request.UserUpdateRequest;
import com.dunglv.identity.dto.response.UserResponse;
import com.dunglv.identity.mapper.IProfileMapper;
import com.dunglv.identity.repository.httpclient.IProfileClient;
import com.dunglv.identity.constant.PredefinedRole;
import com.dunglv.identity.entity.Role;
import com.dunglv.identity.entity.User;
import com.dunglv.identity.exception.AppException;
import com.dunglv.identity.exception.ErrorCode;
import com.dunglv.identity.mapper.IUserMapper;
import com.dunglv.identity.repository.Interface.IRoleRepository;
import com.dunglv.identity.repository.Interface.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    IUserRepository userRepository;
    IUserMapper userMapper;
    PasswordEncoder passwordEncoder;
    IProfileMapper profileMapper;
    IRoleRepository roleRepository;
    IProfileClient profileClient;

    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request) {
        //k cần nữa vì trong enity đã để unique cho username còn bắt trùng thì ở bên dưới có bắt
    //        if (userRepository.existsByUsername(request.getUsername()))
//            throw new AppException(ErrorCode.USER_EXISTED);


        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        // Set mac dinh role cho 1 user moi duoc them vao he thong
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }



        var profileCreationRequest = profileMapper.toProfileCreationRequest(request);

        profileCreationRequest.setUserId(user.getId());

        profileClient.createProfile(profileCreationRequest);

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .chanel("EMAIL")
                .recipient(request.getEmail())
                .subject("Welcome to DLSocialNetwork")
                .body("Welcome " + user.getUsername() + ", your account has been created successfully!")
                .build();

        //Publish message to Kafka
        kafkaTemplate.send("notification-delivery",notificationEvent);

        return userMapper.toUserResponse(user);
    }

    //PostAuthorize is used after the method execution to check if the user has access to the returned object
    @PostAuthorize("returnObject.username == authentication.name") // Ensures that the user can only access their own information
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        //If the user is authenticated, user information saved in the SecurityContextHolder
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName(); // get the username of the authenticated user

        // Find user by username
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
