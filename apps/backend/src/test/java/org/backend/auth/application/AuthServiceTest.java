package org.backend.auth.application;

import org.backend.core.auth.application.AuthService;
import org.backend.core.auth.application.dto.LoginDto;
import org.backend.core.auth.domain.Session;
import org.backend.config.security.JwtService;
import org.backend.core.common.domain.exception.InvalidCredentialsException;
import org.backend.core.user.domain.AuthUser;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepositoryInterface userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_WithValidCredentials_ShouldReturnSession() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        String rawPassword = "password";
        String encodedPassword = "encoded";
        String token = "jwt-token";

        LoginDto dto = new LoginDto();
        dto.setUsername(username);
        dto.setPassword(rawPassword);

        AuthUser authUser = new AuthUser(userId, username, encodedPassword);

        when(userRepository.getAuthUser(username)).thenReturn(authUser);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(userId, username)).thenReturn(token);

        Session result = authService.login(dto);

        assertNotNull(result);
        assertEquals(token, result.token());
        assertEquals(userId, result.userId());
        assertEquals(username, result.username());

        verify(userRepository).getAuthUser(username);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtService).generateToken(userId, username);
    }

    @Test
    void login_WithInvalidPassword_ShouldThrowException() {
        String username = "testuser";
        String rawPassword = "wrong-password";
        String encodedPassword = "encoded";

        LoginDto dto = new LoginDto();
        dto.setUsername(username);
        dto.setPassword(rawPassword);

        AuthUser authUser = new AuthUser(UUID.randomUUID(), username, encodedPassword);

        when(userRepository.getAuthUser(username)).thenReturn(authUser);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> authService.login(dto));
        assertEquals("Invalid credentials", exception.getMessage());

        verify(jwtService, never()).generateToken(any(), any());
    }

    @Test
    void login_WithNonExistentUser_ShouldThrowException() {
        String username = "unknown";

        LoginDto dto = new LoginDto();
        dto.setUsername(username);
        dto.setPassword("password");

        when(userRepository.getAuthUser(username)).thenReturn(null);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> authService.login(dto));
        assertEquals("Invalid credentials", exception.getMessage());

        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any(), any());
    }
}
