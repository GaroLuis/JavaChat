package org.backend.config.security;

import java.security.Principal;
import java.util.UUID;

public record SessionUser(UUID id, String username) implements Principal {
    @Override
    public String getName() {
        return username;
    }
}
