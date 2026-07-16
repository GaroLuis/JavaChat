package org.backend.core.user.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import org.backend.core.user.domain.AuthUser;
import org.backend.core.user.domain.User;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements UserRepositoryInterface {
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getByUserName(String username, boolean exact, @Nullable List<UUID> exclude) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(UserEntity.class);
        var root = cq.from(UserEntity.class);

        var predicates = new ArrayList<Predicate>();
        predicates.add(cb.like(cb.lower(root.get("username")), cb.lower(cb.literal(exact ? username : "%" + username + "%"))));

        if (exclude != null && !exclude.isEmpty()) {
            predicates.add(cb.not(root.get("id").in(exclude)));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .getResultStream()
                .map(UserEntity::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getByIds(List<UUID> ids) {
        var query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.id IN (:userIds)", UserEntity.class
        );

        query.setParameter("userIds", ids);

        return query.getResultStream().map(UserEntity::map).collect(Collectors.toList());
    }

    @Override
    public @Nullable User getById(UUID id) {
        UserEntity entity = entityManager.find(UserEntity.class, id);

        if (null == entity) {
            return null;
        }

        return entity.map();
    }

    @Override
    public @Nullable AuthUser getAuthUser(String username) {
        var query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE LOWER(u.username) = LOWER(:username)", UserEntity.class
        );

        query.setParameter("username", username);

        UserEntity entity = query.getSingleResultOrNull();

        if (null == entity) {
            return null;
        }

        return new AuthUser(entity.getId(), entity.getUsername(), entity.getPassword());
    }
}
