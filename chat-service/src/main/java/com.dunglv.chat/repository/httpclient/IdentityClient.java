package com.dunglv.chat.repository.httpclient;

import com.dunglv.chat.dto.request.IntrospectRequest;
import com.dunglv.chat.dto.response.ApiResponse;
import com.dunglv.chat.dto.response.IntrospectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "identity-service",  url = "${app.services.identity.url}")
public interface IdentityClient {
    @PostMapping("/auth/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request);
}
