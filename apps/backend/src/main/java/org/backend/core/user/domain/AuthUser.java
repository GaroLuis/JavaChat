package org.backend.core.user.domain;

import java.util.UUID;

public record AuthUser(UUID id, String username, String password) {
}
