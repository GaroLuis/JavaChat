package org.backend.core.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UpdateUserConnectionStatusDto {
    @NotNull(message = "User is required")
    private UUID userID;

    @NotNull(message = "connected is required")
    private boolean connected;

    public UpdateUserConnectionStatusDto(UUID userID, boolean connected) {
        this.userID = userID;
        this.connected = connected;
    }
}
