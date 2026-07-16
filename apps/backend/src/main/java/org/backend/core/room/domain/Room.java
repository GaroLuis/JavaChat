package org.backend.core.room.domain;

import org.backend.core.message.domain.Message;
import org.backend.core.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private UUID id;
    private List<User> users;
    private List<Message> messages;

    public Room() {
        this.id = UUID.randomUUID();
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
