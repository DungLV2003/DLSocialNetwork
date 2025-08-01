package com.dunglv.notification.service;

import com.dunglv.notification.dto.request.EmailRequest;
import com.dunglv.notification.dto.request.Recipient;
import com.dunglv.notification.dto.request.SendEmailRequest;
import com.dunglv.notification.dto.request.Sender;
import com.dunglv.notification.dto.response.EmailResponse;
import com.dunglv.notification.exception.AppException;
import com.dunglv.notification.exception.ErrorCode;
import com.dunglv.notification.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    String apiKey = "";

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("DungLe")
                        .email("fenxes20@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}