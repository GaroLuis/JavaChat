package org.backend.core.auth.presentation.mapper;

import org.backend.core.auth.domain.Session;

public class SessionMapper {

    private SessionMapper() {
    }

    public static SessionResponseDto toResponseDto(Session session) {
        SessionResponseDto dto = new SessionResponseDto();
        dto.setToken(session.getToken());
        dto.setUserId(session.getUserId());
        dto.setUsername(session.getUsername());
        return dto;
    }
}
