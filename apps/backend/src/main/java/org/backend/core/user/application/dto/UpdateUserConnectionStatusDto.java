package org.backend.core.user.application.dto;

import java.util.UUID;

public record UpdateUserConnectionStatusDto(UUID userID, boolean connected) {
}
