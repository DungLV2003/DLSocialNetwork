package com.dunglv.notification.controller;


import com.dunglv.event.dto.NotificationEvent;
import com.dunglv.notification.dto.request.Recipient;
import com.dunglv.notification.dto.request.SendEmailRequest;
import com.dunglv.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    EmailService emailService;
    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("Message received from Kafka: {}", message);
        emailService.sendEmail(SendEmailRequest.builder()
                        .to(Recipient.builder()
                                .email(message.getRecipient())
                                .build())
                        .subject(message.getSubject())
                        .htmlContent(message.getBody())
                .build());
    }
}
