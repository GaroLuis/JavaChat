package org.backend.core.room.application.dto;

import java.util.UUID;

public class GetRoomsDto {
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
