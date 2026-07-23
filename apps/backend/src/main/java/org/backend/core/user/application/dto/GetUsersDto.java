package org.backend.core.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetUsersDto {
    private String input = "";

    @NotNull(message = "User is required")
    private UUID userID;
}
