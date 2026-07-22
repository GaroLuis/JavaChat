package org.backend.core.room.presentation.mapper;

import org.backend.core.room.domain.Room;
import org.backend.core.user.presentation.mapper.UserMapper;

import java.util.stream.Collectors;

public class RoomMapper {

    private RoomMapper() {
    }

    public static RoomResponseDto toResponseDto(Room room) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setId(room.getId());
        dto.setUsers(room.getUsers().stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
