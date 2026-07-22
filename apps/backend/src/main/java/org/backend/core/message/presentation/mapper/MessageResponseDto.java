package org.backend.core.message.presentation.mapper;

import lombok.Data;
import org.backend.core.user.presentation.mapper.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageResponseDto {
    private UUID id;
    private String content;
    private UserResponseDto sender;
    private LocalDateTime timestamp;
}
