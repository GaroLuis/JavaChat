package org.backend.core.user.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetUsersDto {
    private String input = "";

    private UUID userID;
}
