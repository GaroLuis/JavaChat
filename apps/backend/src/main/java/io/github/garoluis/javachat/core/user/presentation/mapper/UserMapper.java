package io.github.garoluis.javachat.core.user.presentation.mapper;

import io.github.garoluis.javachat.core.user.domain.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setConnected(user.getConnected());
        dto.setLastConnection(user.getLastConnection());
        return dto;
    }
}
