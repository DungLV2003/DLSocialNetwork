package com.dunglv.identity_service.mapper;

import com.dunglv.identity_service.dto.request.UserCreationRequest;
import com.dunglv.identity_service.dto.request.UserUpdateRequest;
import com.dunglv.identity_service.dto.response.UserResponse;
import com.dunglv.identity_service.entity.User;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") // This annotation allows Spring to manage this mapper as a bean
public interface IUserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
