package org.backend.user.application;

import org.backend.user.application.dto.GetUsersDto;
import org.backend.user.domain.User;

import java.util.Set;

public interface UserServiceInterface {
    public Set<User> get(GetUsersDto dto);
}
