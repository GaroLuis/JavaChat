package org.backend.room.data;

import jakarta.persistence.*;
import org.backend.room.domain.Room;
import org.backend.user.data.UserEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "rooms", schema = "chat")
public class RoomEntity {
    @Id
    private UUID id;

    @ManyToMany
    @JoinTable( name = "users_rooms", schema = "chat", joinColumns =@JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_id") )
    private Set<UserEntity> users;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public RoomEntity() {
        this.users = new HashSet<>();
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
