package org.backend.config.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class JwtHandshakeHandler extends DefaultHandshakeHandler {
    private final JwtServiceInterface jwtService;

    public JwtHandshakeHandler(JwtServiceInterface jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected Principal determineUser(
            @NonNull ServerHttpRequest request,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes
    ) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("session".equals(cookie.getName())) {
                        String token = cookie.getValue();

                        if (jwtService.isTokenValid(token)) {
                            UUID userId = jwtService.getUserIdFromToken(token);
                            String username = jwtService.getUsernameFromToken(token);
                            var principal = new SessionUser(userId, username);
                            var auth = new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    Collections.emptyList()
                            );

                            attributes.put("auth", auth);

                            return auth;
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }
}
