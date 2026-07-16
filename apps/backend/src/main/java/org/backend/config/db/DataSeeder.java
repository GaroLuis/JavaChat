package org.backend.config.db;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.backend.core.message.data.MessageEntity;
import org.backend.core.room.data.RoomEntity;
import org.backend.core.user.data.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (countUsers() > 0) {
            return;
        }

        var alice = createUser("a1b2c3d4-e5f6-7890-abcd-ef1234567890", "alice", true, LocalDateTime.now());
        var bob = createUser("b2c3d4e5-f6a7-8901-bcde-f12345678901", "bob", true, LocalDateTime.now());
        var charlie = createUser("c3d4e5f6-a7b8-9012-cdef-123456789012", "charlie", false, LocalDateTime.of(2026, 7, 10, 14, 30));

        var room1 = createRoom("d4e5f6a7-b8c9-0123-defa-234567890123", alice, bob);
        var room2 = createRoom("e5f6a7b8-c9d0-1234-efab-345678901234", bob, charlie);

        createMessage("f6a7b8c9-d0e1-2345-fabc-456789012345", "Hey Bob!", alice, room1, LocalDateTime.of(2026, 7, 13, 10, 0));
        createMessage("a7b8c9d0-e1f2-3456-abcd-567890123456", "Hey Alice!", bob, room1, LocalDateTime.of(2026, 7, 13, 10, 1));
        createMessage("b8c9d0e1-f2a3-4567-bcde-678901234567", "How are you?", alice, room1, LocalDateTime.of(2026, 7, 13, 10, 2));
        createMessage("c9d0e1f2-a3b4-5678-cdef-789012345678", "Hi Charlie", bob, room2, LocalDateTime.of(2026, 7, 13, 11, 0));
    }

    private long countUsers() {
        return entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u", Long.class).getSingleResult();
    }

    private UserEntity createUser(String id, String username, boolean connected, LocalDateTime lastConnection) {
        var user = new UserEntity();
        user.setId(UUID.fromString(id));
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("password"));
        user.setConnected(connected);
        user.setLastConnection(lastConnection);
        entityManager.persist(user);
        return user;
    }

    private RoomEntity createRoom(String id, UserEntity... users) {
        var room = new RoomEntity();
        room.setId(UUID.fromString(id));
        for (var user : users) {
            room.addUser(user);
        }
        entityManager.persist(room);
        return room;
    }

    private void createMessage(String id, String content, UserEntity sender, RoomEntity room, LocalDateTime timestamp) {
        var message = new MessageEntity();
        message.setId(UUID.fromString(id));
        message.setContent(content);
        message.setSender(sender);
        message.setRoom(room);
        message.setTimestamp(timestamp);
        entityManager.persist(message);
    }
}
