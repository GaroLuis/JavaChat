package org.backend.core.room.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.backend.core.message.data.MessageEntity;
import org.backend.core.room.domain.Room;
import org.backend.core.user.data.UserEntity;

import java.util.*;

@Getter
@Entity
@Table(name = "rooms", schema = "chat")
public class RoomEntity {
    @Setter
    @Id
    private UUID id;

    @ManyToMany
    @JoinTable( name = "users_rooms", schema = "chat", joinColumns =@JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_id") )
    private List<UserEntity> users;

    @OneToMany(mappedBy = "room")
    private List<MessageEntity> messages;

    public RoomEntity() {
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    public Room toDomain() {
        Room room = new Room();

        room.setId(this.id);

        return room;
    }
}
