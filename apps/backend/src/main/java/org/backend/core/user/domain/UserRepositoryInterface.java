package org.backend.core.user.domain;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryInterface {
    public List<User> getUsersByUserName(String username, boolean exact, List<UUID> exclude);

    public List<User> getByIds(List<UUID> ids);

    public @Nullable User getById(UUID id);

    public @Nullable User getByUserName(String username);

    public void updateUser(User user);
}
