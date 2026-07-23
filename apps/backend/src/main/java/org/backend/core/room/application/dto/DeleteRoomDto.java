package org.backend.core.room.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class DeleteRoomDto {
    @NotNull(message = "Room is required")
    private UUID roomId;

    @NotNull(message = "User is required")
    private UUID userId;
}
