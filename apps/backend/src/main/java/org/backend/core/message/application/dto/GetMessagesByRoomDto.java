package org.backend.core.message.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetMessagesByRoomDto {
    @NotNull(message = "Room is required")
    private UUID roomId;

    @NotNull(message = "User is required")
    private UUID userId;
}
