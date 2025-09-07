package com.dunglv.chat.dto.response;


import com.dunglv.chat.entity.ParticipantInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    String id;
    String conversationId;
    boolean me; //tạo cơ sở đánh dấu để chat của mình hiển thị bên phải, còn chat của đối phương hiển thị bên trái
    String message;
    ParticipantInfo sender;
    Instant createdDate;
}