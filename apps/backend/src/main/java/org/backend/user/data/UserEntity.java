package org.backend.user.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.backend.user.domain.User;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "chat")
public class UserEntity {
    @Id
    private UUID id;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "connected", nullable = false)
    private Boolean connected = false;

    @Column(name = "last_connection", nullable = true)
    private @Nullable LocalDateTime lastConnection;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public @Nullable LocalDateTime getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(@Nullable LocalDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    public User map() {
        User user = new User(this.username);

        user.setId(this.id);
        user.setUsername(this.username);
        user.setConnected(this.connected);
        user.setLastConnection(this.lastConnection);

        return user;
    }
}
