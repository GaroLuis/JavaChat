package org.backend.core.room.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class CreateRoomDto {
    @Valid
    private List<@NotNull(message = "User cannot be null") UUID> userIds;

    public void addUserId(UUID id) {
        userIds.add(id);
    }
}
