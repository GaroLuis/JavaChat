package org.backend.core.room.data;

import jakarta.persistence.*;
import org.backend.core.message.data.MessageEntity;
import org.backend.core.room.domain.Room;
import org.backend.core.user.data.UserEntity;

import java.util.*;

@Entity
@Table(name = "rooms", schema = "chat")
public class RoomEntity {
    @Id
    private UUID id;

    @ManyToMany
    @JoinTable( name = "users_rooms", schema = "chat", joinColumns =@JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_id") )
    private List<UserEntity> users;

    @OneToMany(mappedBy = "room")
    private List<MessageEntity> messages;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoomEntity() {
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    public Room map() {
        Room room = new Room();

        room.setId(this.id);

        return room;
    }
}
