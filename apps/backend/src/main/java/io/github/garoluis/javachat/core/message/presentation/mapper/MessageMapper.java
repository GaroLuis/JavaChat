package io.github.garoluis.javachat.core.message.presentation.mapper;

import io.github.garoluis.javachat.core.message.domain.Message;
import io.github.garoluis.javachat.core.user.presentation.mapper.UserMapper;

public class MessageMapper {

    private MessageMapper() {
    }

    public static MessageResponseDto toResponseDto(Message message) {
        MessageResponseDto dto = new MessageResponseDto();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSender(UserMapper.toResponseDto(message.getSender()));
        dto.setTimestamp(message.getTimestamp());
        dto.setRoom(message.getRoom());
        return dto;
    }
}
