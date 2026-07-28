package org.backend.core.message.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class GetMessagesByRoomDto {
    @NotNull(message = "Room is required")
    private UUID roomId;

    @NotNull(message = "User is required")
    private UUID userId;

    private @Nullable LocalDateTime cursor;

    private int size = 50;
}
