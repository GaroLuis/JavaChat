package io.github.garoluis.javachat.core.auth.application;

import io.github.garoluis.javachat.core.auth.application.dto.LoginDto;
import io.github.garoluis.javachat.core.auth.domain.Session;
import io.github.garoluis.javachat.config.security.JwtServiceInterface;
import io.github.garoluis.javachat.core.common.domain.exception.InvalidCredentialsException;
import io.github.garoluis.javachat.core.user.domain.User;
import io.github.garoluis.javachat.core.user.domain.UserRepositoryInterface;
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
