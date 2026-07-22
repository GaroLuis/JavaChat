package org.backend.core.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import org.backend.core.auth.application.AuthServiceInterface;
import org.backend.core.auth.application.dto.LoginDto;
import org.backend.core.auth.presentation.mapper.SessionResponseDto;
import org.backend.config.security.CookieServiceInterface;
import org.backend.core.auth.presentation.mapper.SessionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceInterface authService;
    private final CookieServiceInterface cookieService;
    private final int maxAge;

    public AuthController(
            AuthServiceInterface authService,
            CookieServiceInterface cookieService,
            @Value("${security.jwt.expiration}") int maxAge

    ) {
        this.authService = authService;
        this.cookieService = cookieService;
        this.maxAge = maxAge;
    }

    @PostMapping("/login")
    public SessionResponseDto login(@RequestBody LoginDto dto, HttpServletResponse response) {
        SessionResponseDto sessionDto = SessionMapper.toResponseDto(authService.login(dto));

        cookieService.addCookie("session", sessionDto.getToken(), maxAge, response);

        return sessionDto;
    }
}
