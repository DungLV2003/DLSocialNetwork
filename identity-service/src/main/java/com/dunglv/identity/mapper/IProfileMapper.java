package com.dunglv.identity.mapper;

import com.dunglv.identity.dto.request.ProfileCreationRequest;
import com.dunglv.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
