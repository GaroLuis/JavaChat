package org.backend.core.auth.presentation.mapper;

import org.backend.core.auth.domain.Session;

public class SessionMapper {

    private SessionMapper() {
    }

    public static SessionResponseDto toResponseDto(Session session) {
        SessionResponseDto dto = new SessionResponseDto();
        dto.setToken(session.token());
        dto.setUserId(session.userId());
        dto.setUsername(session.username());
        return dto;
    }
}
