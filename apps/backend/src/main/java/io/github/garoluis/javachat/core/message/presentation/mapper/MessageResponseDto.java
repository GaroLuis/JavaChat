package io.github.garoluis.javachat.core.message.presentation.mapper;

import lombok.Data;
import io.github.garoluis.javachat.core.room.domain.Room;
import io.github.garoluis.javachat.core.user.presentation.mapper.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageResponseDto {
    private UUID id;
    private String content;
    private UserResponseDto sender;
    private LocalDateTime timestamp;
    private Room room;
}
