package org.backend.config.security;

import java.util.UUID;

public interface JwtServiceInterface {
    public String generateToken(UUID userId, String username);

    public UUID getUserIdFromToken(String token);

    public String getUsernameFromToken(String token);

    public boolean isTokenValid(String token);
}
