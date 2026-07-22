package org.backend.core.message.presentation.mapper;

import org.backend.core.message.domain.Message;
import org.backend.core.user.presentation.mapper.UserMapper;

public class MessageMapper {

    private MessageMapper() {
    }

    public static MessageResponseDto toResponseDto(Message message) {
        MessageResponseDto dto = new MessageResponseDto();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSender(UserMapper.toResponseDto(message.getSender()));
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}
