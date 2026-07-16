package org.backend.core.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import org.backend.core.auth.application.AuthServiceInterface;
import org.backend.core.auth.application.dto.LoginDto;
import org.backend.core.auth.domain.Session;
import org.backend.config.security.CookieServiceInterface;
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
    public Session login(@RequestBody LoginDto dto, HttpServletResponse response) {
        Session session = authService.login(dto);

        cookieService.addCookie("session", session.getToken(), maxAge, response);

        return session;
    }
}
