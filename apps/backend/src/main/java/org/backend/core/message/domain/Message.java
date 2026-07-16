package org.backend.core.message.domain;

import org.backend.core.room.domain.Room;
import org.backend.core.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private UUID id;
    private String content;
    private User sender;
    private Room room;
    private LocalDateTime timestamp;

    public Message(String content, User sender, Room room) {
        this.content = content;
        this.sender = sender;
        this.room = room;

        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
