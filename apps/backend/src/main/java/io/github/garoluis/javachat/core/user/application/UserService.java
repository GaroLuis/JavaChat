package io.github.garoluis.javachat.core.user.application;

import io.github.garoluis.javachat.core.common.domain.exception.ConflictException;
import io.github.garoluis.javachat.core.common.domain.exception.NotFoundException;
import io.github.garoluis.javachat.core.user.application.dto.CreateUserDto;
import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserConnectionStatusDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserDto;
import io.github.garoluis.javachat.core.user.domain.User;
import io.github.garoluis.javachat.core.user.domain.UserRepositoryInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryInterface userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public User create(CreateUserDto dto) {
        if (userRepository.getByUserName(dto.getUsername()) != null) {
            throw new ConflictException("Username already taken");
        }

        User user = new User(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.create(user);
    }

    @Override
    public User update(UpdateUserDto dto) {
        User user = userRepository.getById(dto.getUserId());

        if (null == user) {
            throw new NotFoundException("User not found");
        }

        if (dto.getUsername() != null) {
            User existing = userRepository.getByUserName(dto.getUsername());

            if (existing != null && !existing.getId().equals(user.getId())) {
                throw new ConflictException("Username already taken");
            }

            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.updateUser(user);

        return user;
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
