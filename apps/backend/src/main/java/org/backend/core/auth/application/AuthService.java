package org.backend.core.auth.application;

import org.backend.core.auth.application.dto.LoginDto;
import org.backend.core.auth.domain.Session;
import org.backend.config.security.JwtServiceInterface;
import org.backend.core.common.domain.exception.InvalidCredentialsException;
import org.backend.core.user.domain.AuthUser;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceInterface {
    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceInterface jwtService;

    public AuthService(
            UserRepositoryInterface userRepository,
            PasswordEncoder passwordEncoder,
            JwtServiceInterface jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Session login(LoginDto dto) {
        AuthUser user = userRepository.getAuthUser(dto.getUsername());

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.password())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.id(), user.username());
        return new Session(token, user.id(), user.username());
    }
}
