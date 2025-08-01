package com.dunglv.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent {
    String chanel;   //Ex: Zalo, Mail, Slack
    String recipient;
//    String templateCode;
//    Map<String, Object> param;
    String subject;
    String body;

}
