package org.backend.room.application.dto;

import java.util.List;
import java.util.UUID;

public class CreateRoomDto {
    private List<UUID> userIds;

    public List<UUID> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UUID> userIds) {
        this.userIds = userIds;
    }
}
