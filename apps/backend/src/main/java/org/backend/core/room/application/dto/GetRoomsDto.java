package org.backend.core.room.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetRoomsDto {
    private UUID userId;
}
