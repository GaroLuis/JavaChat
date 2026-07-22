package org.backend.core.message.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateMessageDto {
    private UUID senderId;

    private UUID roomId;

    private String content;
}
