package org.backend.core.auth.application;

import org.backend.core.auth.application.dto.LoginDto;
import org.backend.core.auth.domain.Session;

public interface AuthServiceInterface {
    public Session login(LoginDto dto);
}
