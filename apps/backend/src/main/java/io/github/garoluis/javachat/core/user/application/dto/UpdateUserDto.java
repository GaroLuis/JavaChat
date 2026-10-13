package io.github.garoluis.javachat.core.user.application.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Setter
@Getter
public class UpdateUserDto {
    private UUID userId;

    @Size(max = 100, message = "User name cannot exceed 100 characters")
    @Pattern(regexp = ".*\\S.*", message = "User name cannot be blank")
    private @Nullable String username;

    private @Nullable String password;
}
