package org.backend.core.auth.application;

import org.backend.core.auth.application.dto.LoginDto;
import org.backend.core.auth.domain.Session;
import org.backend.config.security.JwtServiceInterface;
import org.backend.core.common.domain.exception.InvalidCredentialsException;
import org.backend.core.user.domain.User;
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
        User user = userRepository.getByUserName(dto.getUsername());

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.getId(), user.getUsername());
        return new Session(token, user.getId(), user.getUsername());
    }
}
