package org.backend.message.data;

import jakarta.persistence.*;
import org.backend.room.data.RoomEntity;
import org.backend.user.data.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages", schema = "chat")
public class MessageEntity {
    @Id
    private UUID id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    private UserEntity sender;

    @ManyToOne
    private RoomEntity room;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

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

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
