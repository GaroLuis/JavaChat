package org.backend.core.user.presentation.mapper;

import lombok.Data;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID id;
    private String username;
    private Boolean connected;
    private @Nullable LocalDateTime lastConnection;
}
