package io.github.garoluis.javachat.core.auth.presentation.mapper;

import io.github.garoluis.javachat.core.auth.domain.Session;

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
