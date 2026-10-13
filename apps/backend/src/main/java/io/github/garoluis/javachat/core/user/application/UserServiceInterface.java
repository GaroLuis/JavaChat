package io.github.garoluis.javachat.core.user.application;

import io.github.garoluis.javachat.core.user.application.dto.CreateUserDto;
import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserConnectionStatusDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserDto;
import io.github.garoluis.javachat.core.user.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    public List<User> getUsers(GetUsersDto dto);

    public User getUser(UUID id);

    public User create(CreateUserDto dto);

    public User update(UpdateUserDto dto);

    public void updateUserConnectionStatus(UpdateUserConnectionStatusDto dto);
}
