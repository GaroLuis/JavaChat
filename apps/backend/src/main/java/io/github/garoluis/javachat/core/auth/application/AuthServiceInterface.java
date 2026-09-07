package io.github.garoluis.javachat.core.auth.application;

import io.github.garoluis.javachat.core.auth.application.dto.LoginDto;
import io.github.garoluis.javachat.core.auth.domain.Session;

public interface AuthServiceInterface {
    public Session login(LoginDto dto);
}
