package org.backend.core.message.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateMessageDto {
    @NotNull(message = "Sender is required")
    private UUID senderId;

    @NotNull(message = "Room is required")
    private UUID roomId;

    @NotBlank(message = "Content cannot be blank")
    private String content;
}
