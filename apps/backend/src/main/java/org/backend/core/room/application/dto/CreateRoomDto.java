package org.backend.core.room.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class CreateRoomDto {
    private List<UUID> userIds;

    public void addUserId(UUID id) {
        userIds.add(id);
    }
}
