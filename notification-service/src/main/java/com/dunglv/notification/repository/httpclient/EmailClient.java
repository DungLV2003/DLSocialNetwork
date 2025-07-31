package com.dunglv.notification.repository.httpclient;

import com.dunglv.notification.dto.request.EmailRequest;
import com.dunglv.notification.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "email-client", url = "${notification.email.mailersend-url}")
public interface EmailClient {
    @PostMapping(value = "/v1/email", produces = MediaType.APPLICATION_JSON_VALUE)
    EmailResponse sendEmail(@RequestHeader("Authorization") String apiKey, @RequestBody EmailRequest body);
}