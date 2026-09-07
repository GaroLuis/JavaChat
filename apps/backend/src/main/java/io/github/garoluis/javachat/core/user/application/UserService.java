package io.github.garoluis.javachat.core.user.application;

import io.github.garoluis.javachat.core.common.domain.exception.NotFoundException;
import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserConnectionStatusDto;
import io.github.garoluis.javachat.core.user.domain.User;
import io.github.garoluis.javachat.core.user.domain.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return userRepository.getUsersByUserName(dto.getInput(), false, List.of(dto.getUserID()));
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.getById(id);
    }

    @Override
    public void updateUserConnectionStatus(UpdateUserConnectionStatusDto dto) {
        User user = userRepository.getById(dto.getUserID());

        if (null == user) {
            throw new NotFoundException("User not found");
        }

        user.setConnected(dto.isConnected());

        if (!dto.isConnected()) {
            user.setLastConnection(LocalDateTime.now());
        }

        userRepository.updateUser(user);
    }
}
