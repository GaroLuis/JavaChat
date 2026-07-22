package org.backend.core.message.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.backend.core.message.domain.Message;
import org.backend.core.room.data.RoomEntity;
import org.backend.core.user.data.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
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

    public Message toDomain() {
        Message message = new Message(this.content, this.sender.toDomain(), this.room.toDomain());

        message.setId(this.id);
        message.setTimestamp(this.timestamp);

        return message;
    }
}
