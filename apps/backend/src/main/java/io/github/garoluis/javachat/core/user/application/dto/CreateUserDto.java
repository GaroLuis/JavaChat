package io.github.garoluis.javachat.core.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserDto {
    @NotBlank(message = "User name cannot be blank")
    @Size(max = 100, message = "User name cannot exceed 100 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
