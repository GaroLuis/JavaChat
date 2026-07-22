package org.backend.core.user.presentation.mapper;

import org.backend.core.user.domain.User;

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
