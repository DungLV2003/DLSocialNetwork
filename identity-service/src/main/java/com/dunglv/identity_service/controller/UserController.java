package com.dunglv.identity_service.controller;

import com.dunglv.identity_service.dto.request.UserCreationRequest;
import com.dunglv.identity_service.dto.request.UserUpdateRequest;
import com.dunglv.identity_service.dto.response.ApiResponse;
import com.dunglv.identity_service.dto.response.UserResponse;
import com.dunglv.identity_service.entity.User;
import com.dunglv.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }


    @GetMapping("/{userid}")
    UserResponse getUserById(@PathVariable String userid) {
        return userService.getUserById(userid);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
    @PutMapping("/{userid}")
    UserResponse updateUser(@PathVariable String userid, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userid, request);
    }

    @DeleteMapping("/{userid}")
    String deleteUser(@PathVariable String userid) {
        userService.deleteUser(userid);
        return "User deleted successfully";
    }
}
