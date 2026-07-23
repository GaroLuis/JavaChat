package org.backend.core.room.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetRoomsDto {
    @NotNull(message = "User is required")
    private UUID userId;
}
