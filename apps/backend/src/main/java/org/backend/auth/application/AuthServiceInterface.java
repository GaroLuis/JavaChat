package org.backend.auth.application;

import org.backend.auth.application.dto.LoginDto;
import org.backend.auth.domain.Session;

public interface AuthServiceInterface {
    public Session login(LoginDto dto);
}
