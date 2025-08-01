package com.dunglv.identity_service.repository.httpclient;

import com.dunglv.identity_service.configuration.AuthenticationRequestInterceptor;
import com.dunglv.identity_service.dto.request.ProfileCreationRequest;
import com.dunglv.identity_service.dto.response.ApiResponse;
import com.dunglv.identity_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "profile-service", url = "${app.services.profile.url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IProfileClient {
    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);

}
