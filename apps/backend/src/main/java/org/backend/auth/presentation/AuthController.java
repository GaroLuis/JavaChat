package org.backend.auth.presentation;

import org.backend.auth.application.AuthServiceInterface;
import org.backend.auth.application.dto.LoginDto;
import org.backend.auth.domain.Session;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceInterface authService;

    public AuthController(AuthServiceInterface authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Session login(@RequestBody LoginDto request) {
        return authService.login(request);
    }
}
