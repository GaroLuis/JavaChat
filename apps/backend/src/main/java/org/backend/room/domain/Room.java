package org.backend.room.domain;

import org.backend.user.domain.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Room {
    private UUID id;
    private Set<User> users;

    public Room() {
        this.id = UUID.randomUUID();
        this.users = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
