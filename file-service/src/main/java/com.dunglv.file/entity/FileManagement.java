package com.dunglv.file.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "file_management")
public class FileManagement {
    @MongoId
    String id;
    String ownerId;
    String contentType;
    String path;
    long size;
    String md5Checksum;

}
