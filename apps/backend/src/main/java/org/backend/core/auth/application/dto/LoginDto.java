package org.backend.core.auth.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    @NotBlank(message = "User name cannot be blank")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;
}
