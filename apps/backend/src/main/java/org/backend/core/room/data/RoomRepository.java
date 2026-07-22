package org.backend.core.room.data;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.backend.core.message.data.MessageEntity;
import org.backend.core.room.domain.Room;
import org.backend.core.room.domain.RoomRepositoryInterface;
import org.backend.core.user.data.UserEntity;
import org.backend.core.user.domain.User;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RoomRepository implements RoomRepositoryInterface {
    private final EntityManager entityManager;

    public RoomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Room create(Room room) {
        List<UUID> userIds = room.getUsers().stream().map(User::getId).toList();

        var query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.id IN :userIds", UserEntity.class
        );

        query.setParameter("userIds", userIds);

        List<UserEntity> users = query.getResultList();

        RoomEntity entity = new RoomEntity();
        entity.setId(room.getId());
        users.forEach(entity::addUser);

        entityManager.persist(entity);
        entityManager.flush();

        return room;
    }

    @Override
    public void delete(Room room) {
        RoomEntity entity = entityManager.find(RoomEntity.class, room.getId());

        if (null == entity) {
            return;
        }

        for (MessageEntity message : entity.getMessages()) {
            entityManager.remove(message);
        }

        entityManager.remove(entity);
        entityManager.flush();
    }

    @Override
    public List<Room> getByUser(UUID userId) {
        var roomsQuery = entityManager.createQuery(
                """
                            SELECT r
                            FROM RoomEntity r
                            JOIN r.users u
                            WHERE u.id = :userId
                        """, RoomEntity.class
        );
        roomsQuery.setParameter("userId", userId);
        var roomEntities = roomsQuery.getResultList();

        return roomEntities.stream().map(r -> {
            Room room = r.toDomain();

            r.getUsers().forEach(u -> room.addUser(u.toDomain()));

            return room;
        }).collect(Collectors.toList());
    }

    @Override
    public @Nullable Room getById(UUID id) {
        var roomsQuery = entityManager.createQuery(
                """
                            SELECT r
                            FROM RoomEntity r
                            JOIN FETCH r.users
                            WHERE r.id = :id
                        """,
                RoomEntity.class
        );

        roomsQuery.setParameter("id", id);
        RoomEntity entity = roomsQuery.getSingleResultOrNull();

        if (null == entity) {
            return null;
        }

        Room room = entity.toDomain();

        entity.getUsers().forEach(u -> room.addUser(u.toDomain()));

        return room;
    }
}
