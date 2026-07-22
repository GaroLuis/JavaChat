package org.backend.core.user.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.backend.core.user.domain.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "chat")
public class UserEntity {
    @Setter
    @Getter
    @Id
    private UUID id;

    @Setter
    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Setter
    @Column(name = "connected", nullable = false)
    private boolean connected;

    @Column(name = "last_connection")
    private @Nullable LocalDateTime lastConnection;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    public @NonNull String getUsername() {
        return username;
    }

    public @Nullable LocalDateTime getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(@Nullable LocalDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    public User toDomain() {
        User user = new User(this.username);

        user.setId(this.id);
        user.setUsername(this.username);
        user.setConnected(this.connected);
        user.setLastConnection(this.lastConnection);

        return user;
    }
}
