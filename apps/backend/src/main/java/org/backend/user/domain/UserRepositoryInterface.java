package org.backend.user.domain;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserRepositoryInterface {
    public Set<User> getByUserName(String username);

    public Set<User> getByIds(List<UUID> ids);

    public @Nullable User getById(UUID id);
}
