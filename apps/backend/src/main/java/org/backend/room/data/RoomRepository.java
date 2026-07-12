package org.backend.room.data;

import jakarta.persistence.EntityManager;
import org.backend.room.domain.Room;
import org.backend.room.domain.RoomRepositoryInterface;
import org.backend.user.data.UserEntity;
import org.backend.user.domain.User;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RoomRepository implements RoomRepositoryInterface {
    private final EntityManager entityManager;

    public RoomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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

        entityManager.remove(entity);
        entityManager.flush();
    }

    @Override
    public Set<Room> getByUser(UUID userId) {
        var query = entityManager.createQuery(
                "SELECT r FROM RoomEntity r JOIN FETCH r.users u WHERE u.id = :userId", RoomEntity.class
        );

        query.setParameter("userId", userId);

        return query.getResultStream().map(r -> {
            Room room = new Room();

            for (UserEntity u : r.getUsers()) {
                room.addUser(u.map());
            }

            return room;
        }).collect(Collectors.toSet());
    }

    @Override
    public @Nullable Room getById(UUID id) {
        var query = entityManager.createQuery(
                "SELECT r FROM RoomEntity r JOIN FETCH r.users WHERE r.id = :id",
                RoomEntity.class
        );

        query.setParameter("id", id);

        RoomEntity entity = query.getSingleResultOrNull();

        if (null == entity) {
            return null;
        }

        Room room = entity.map();

        for (UserEntity userEntity : entity.getUsers()) {
            room.addUser(userEntity.map());
        }

        return room;
    }
}
