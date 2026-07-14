package org.backend.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private final String base64Secret = "dGhpcyBpcyBhIHNlY3JldCBrZXkgZm9yIEphdmFDaGF0IGJhY2tlbmQgc2VjcmV0";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(base64Secret, 86400000);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";

        String token = jwtService.generateToken(userId, username);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void getUserIdFromToken_ShouldReturnCorrectUserId() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";

        String token = jwtService.generateToken(userId, username);
        UUID extractedUserId = jwtService.getUserIdFromToken(token);

        assertEquals(userId, extractedUserId);
    }

    @Test
    void getUsernameFromToken_ShouldReturnCorrectUsername() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";

        String token = jwtService.generateToken(userId, username);
        String extractedUsername = jwtService.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";

        String token = jwtService.generateToken(userId, username);

        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void isTokenValid_WithInvalidToken_ShouldReturnFalse() {
        assertFalse(jwtService.isTokenValid("invalid-token"));
    }

    @Test
    void isTokenValid_WithExpiredToken_ShouldReturnFalse() {
        jwtService = new JwtService(base64Secret, -1);
        String token = jwtService.generateToken(UUID.randomUUID(), "testuser");

        assertFalse(jwtService.isTokenValid(token));
    }
}
