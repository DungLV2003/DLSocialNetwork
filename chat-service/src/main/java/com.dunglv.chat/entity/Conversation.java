package com.dunglv.chat.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Conversation {
    @MongoId
    String id;

    String type; // GROUP, DIRECT

    //String groupOwner;
    //String groupAvater;

    @Indexed(unique = true)
    String participantsHash; //Kết hợp 2 UserId khi dùng DIRECT (không dùng cho group bởi vì 2 group khác nhau có thể có cùng
    //thành viên như nhau )
    //Biến này sinh ra để đảm bảo có 1 conversation giữa 2 người là duy nhất

    List<ParticipantInfo> participants;

    Instant createdDate;

    Instant modifiedDate;
}