package io.github.garoluis.javachat.core.message.presentation.mapper;

import io.github.garoluis.javachat.core.room.presentation.mapper.RoomResponseDto;
import io.github.garoluis.javachat.core.user.presentation.mapper.UserResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageResponseDto {
    private UUID id;
    private String content;
    private UserResponseDto sender;
    private LocalDateTime timestamp;
    private RoomResponseDto room;
}
