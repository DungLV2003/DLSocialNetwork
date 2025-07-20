package com.dunglv.identity_service.mapper;

import com.dunglv.identity_service.dto.request.ProfileCreationRequest;
import com.dunglv.identity_service.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
