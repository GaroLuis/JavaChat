package org.backend.config.security;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieServiceInterface {
    public void addCookie(String name, String value, int maxAge, HttpServletResponse response);

    public void deleteCookie(String name, HttpServletResponse response);
}
