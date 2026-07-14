package org.backend.user.application;

import org.backend.user.application.dto.GetUsersDto;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;

    public UserService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> get(GetUsersDto dto) {
        return userRepository.getByUserName(dto.getInput(), false);
    }
}
