package org.backend.core.auth.presentation.mapper;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionResponseDto {
    private String token;
    private UUID userId;
    private String username;
}
