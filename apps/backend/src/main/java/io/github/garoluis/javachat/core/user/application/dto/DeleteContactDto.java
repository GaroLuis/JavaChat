package io.github.garoluis.javachat.core.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class DeleteContactDto {
    @NotNull(message = "User is required")
    private UUID userId;

    @NotNull(message = "Contact is required")
    private UUID contactId;
}
