package org.backend.core.user.application;

import org.backend.core.user.application.dto.GetUsersDto;
import org.backend.core.user.domain.User;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;

    public UserService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(GetUsersDto dto) {
        return userRepository.getByUserName(dto.getInput(), false, List.of(dto.getUserID()));
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.getById(id);
    }
}
