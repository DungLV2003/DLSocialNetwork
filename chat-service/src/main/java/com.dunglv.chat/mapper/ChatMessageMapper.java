package com.dunglv.chat.mapper;

import com.dunglv.chat.dto.request.ChatMessageRequest;
import com.dunglv.chat.dto.response.ChatMessageResponse;
import com.dunglv.chat.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

    ChatMessage toChatMessage(ChatMessageRequest request);

    List<ChatMessageResponse> toChatMessageResponses(List<ChatMessage> chatMessages);
}
