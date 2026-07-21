package org.backend.core.user.application.dto;

import java.util.UUID;

public class UpdateUserConnectionStatusDto {
    private UUID userID;

    private boolean connected;

    public UpdateUserConnectionStatusDto(UUID userID, boolean connected) {
        this.userID = userID;
        this.connected = connected;
    }

    public UUID getUserID() {
        return userID;
    }

    public boolean isConnected() {
        return connected;
    }
}
