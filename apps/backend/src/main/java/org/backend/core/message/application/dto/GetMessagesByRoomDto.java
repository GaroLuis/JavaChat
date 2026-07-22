package org.backend.core.message.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetMessagesByRoomDto {
    private UUID roomId;

    private UUID userId;
}
