package org.backend.core.user.domain;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class User {
    private UUID id;
    private String username;
    private Boolean connected;
    private @Nullable LocalDateTime lastConnection;

    public User(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.connected = false;
    }
}
