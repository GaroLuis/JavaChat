package org.backend.user.data;

import jakarta.persistence.EntityManager;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements UserRepositoryInterface {
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<User> getByUserName(String username) {
        var query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE LOWER(u.username) LIKE LOWER(:username)", UserEntity.class
        );

        query.setParameter("username", "%" + username + "%");

        return query.getResultStream().map(UserEntity::map).collect(Collectors.toSet());
    }

    @Override
    public Set<User> getByIds(List<UUID> ids) {
        var query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.id IN (:userIds)", UserEntity.class
        );

        query.setParameter("userIds", ids);

        return query.getResultStream().map(UserEntity::map).collect(Collectors.toSet());
    }

    @Override
    public @Nullable User getById(UUID id) {
        UserEntity entity = entityManager.find(UserEntity.class, id);

        if (null == entity) {
            return null;
        }

        return entity.map();
    }
}
