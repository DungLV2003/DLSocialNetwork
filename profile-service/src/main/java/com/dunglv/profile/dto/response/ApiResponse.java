package com.dunglv.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON serialization
public class ApiResponse<T> {
     //If not set default code is 1000 then code is 0
     @Builder.Default
     int code = 1000;
     String message;
     T result;
}