package org.backend.core.room.presentation.mapper;

import lombok.Data;
import org.backend.core.user.presentation.mapper.UserResponseDto;

import java.util.List;
import java.util.UUID;

@Data
public class RoomResponseDto {
    private UUID id;
    private List<UserResponseDto> users;
}
