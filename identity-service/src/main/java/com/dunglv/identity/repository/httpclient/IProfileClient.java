package com.dunglv.identity.repository.httpclient;

import com.dunglv.identity.dto.request.ProfileCreationRequest;
import com.dunglv.identity.dto.response.ApiResponse;
import com.dunglv.identity.configuration.AuthenticationRequestInterceptor;
import com.dunglv.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "profile-service", url = "${app.services.profile.url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IProfileClient {
    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);

}
