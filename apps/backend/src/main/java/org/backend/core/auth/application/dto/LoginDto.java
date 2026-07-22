package org.backend.core.auth.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    private String username;

    private String password;
}
