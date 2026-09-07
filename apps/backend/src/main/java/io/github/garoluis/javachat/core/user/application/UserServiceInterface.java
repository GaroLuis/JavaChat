package io.github.garoluis.javachat.core.user.application;

import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserConnectionStatusDto;
import io.github.garoluis.javachat.core.user.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    public List<User> getUsers(GetUsersDto dto);

    public User getUser(UUID id);

    public void updateUserConnectionStatus(UpdateUserConnectionStatusDto dto);
}
