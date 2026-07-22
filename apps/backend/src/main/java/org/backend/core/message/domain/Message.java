package org.backend.core.message.domain;

import lombok.Getter;
import lombok.Setter;
import org.backend.core.room.domain.Room;
import org.backend.core.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
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
}
