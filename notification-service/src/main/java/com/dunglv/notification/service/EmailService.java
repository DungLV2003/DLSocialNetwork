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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    String apiKey = "";

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .from(Sender.builder()
                        .email("dunglvhe17249@fpt.edu.vn")
                        .name("Dung Le")
                        .build())
                .to(List.of(Recipient.builder()
                        .email("dunglvhe17249@fpt.edu.vn")
                        .name("Dung Le")
                        .build()))
                .subject(request.getSubject())
                .html(request.getHtml())
                .build();
        try {
            return emailClient.sendEmail("Bearer " + apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
