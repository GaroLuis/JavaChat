package org.backend.core.user.application;

import org.backend.core.user.application.dto.GetUsersDto;
import org.backend.core.user.application.dto.UpdateUserConnectionStatusDto;
import org.backend.core.user.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    public List<User> getUsers(GetUsersDto dto);

    public User getUser(UUID id);

    public void updateUserConnectionStatus(UpdateUserConnectionStatusDto dto);
}
