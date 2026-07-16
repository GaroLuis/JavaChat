package org.backend.core.room.application.dto;

import java.util.UUID;

public class DeleteRoomDto {
    private UUID roomId;
    private UUID userId;

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
