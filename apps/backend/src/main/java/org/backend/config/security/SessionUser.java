package org.backend.config.security;

import java.util.UUID;

public record SessionUser(UUID id, String username) {
}
