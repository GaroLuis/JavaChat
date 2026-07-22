package org.backend.core.room.domain;

import lombok.Getter;
import lombok.Setter;
import org.backend.core.message.domain.Message;
import org.backend.core.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Room {
    @Setter
    private UUID id;

    @Setter
    private List<User> users;

    private final List<Message> messages;

    public Room() {
        this.id = UUID.randomUUID();
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
