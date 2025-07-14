package com.dunglv.profile.mapper;

import com.dunglv.profile.dto.request.ProfileCreationRequest;
import com.dunglv.profile.dto.response.UserProfileResponse;
import com.dunglv.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
