package com.dunglv.chat.dto.response;

import com.dunglv.chat.entity.ParticipantInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponse {
    String id;
    String type; // GROUP, DIRECT
    String participantsHash;
    String conversationAvatar; //Khi user A chat với user B thì conversationAvatar = avatar của user B
    String conversationName; //Khi user A chat với user B thì conversationName = name của user B
    List<ParticipantInfo> participants;
    Instant createdDate;
    Instant modifiedDate;
}
