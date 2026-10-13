package io.github.garoluis.javachat.user.application;

import io.github.garoluis.javachat.core.common.domain.exception.ConflictException;
import io.github.garoluis.javachat.core.common.domain.exception.NotFoundException;
import io.github.garoluis.javachat.core.user.application.UserService;
import io.github.garoluis.javachat.core.user.application.dto.CreateUserDto;
import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserDto;
import io.github.garoluis.javachat.core.user.domain.User;
import io.github.garoluis.javachat.core.user.domain.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryInterface userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void get_ShouldReturnUsersMatchingInput() {
        String input = "john";
        UUID userId = UUID.randomUUID();
        GetUsersDto dto = new GetUsersDto();
        dto.setInput(input);
        dto.setUserID(userId);

        User user1 = new User("john_doe");
        User user2 = new User("johnny");
        List<User> expectedUsers = List.of(user1, user2);

        when(userRepository.getUsersByUserName(input, false, List.of(userId))).thenReturn(expectedUsers);

        List<User> result = userService.getUsers(dto);

        assertEquals(expectedUsers, result);
        verify(userRepository).getUsersByUserName(input, false, List.of(userId));
    }

    @Test
    void create_ShouldHashPasswordAndCreateUser() {
        CreateUserDto dto = new CreateUserDto();
        dto.setUsername("newuser");
        dto.setPassword("secret");

        when(userRepository.getByUserName("newuser")).thenReturn(null);
        when(passwordEncoder.encode("secret")).thenReturn("hashed-secret");
        when(userRepository.create(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.create(dto);

        assertNotNull(result.getId());
        assertEquals("newuser", result.getUsername());
        assertEquals("hashed-secret", result.getPassword());
        assertEquals(Boolean.FALSE, result.getConnected());

        verify(userRepository).create(any(User.class));
    }

    @Test
    void create_WhenUsernameTaken_ShouldThrowConflict() {
        CreateUserDto dto = new CreateUserDto();
        dto.setUsername("alice");
        dto.setPassword("secret");

        when(userRepository.getByUserName("alice")).thenReturn(new User("alice"));

        assertThrows(ConflictException.class, () -> userService.create(dto));

        verify(userRepository, never()).create(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void update_ShouldUpdateUsernameAndPassword() {
        UUID userId = UUID.randomUUID();
        User existing = new User("oldname");
        existing.setId(userId);
        existing.setPassword("old-hash");

        UpdateUserDto dto = new UpdateUserDto();
        dto.setUserId(userId);
        dto.setUsername("newname");
        dto.setPassword("new-secret");

        when(userRepository.getById(userId)).thenReturn(existing);
        when(userRepository.getByUserName("newname")).thenReturn(null);
        when(passwordEncoder.encode("new-secret")).thenReturn("new-hash");

        User result = userService.update(dto);

        assertEquals("newname", result.getUsername());
        assertEquals("new-hash", result.getPassword());

        verify(userRepository).updateUser(existing);
    }

    @Test
    void update_WithOnlyPassword_ShouldNotChangeUsername() {
        UUID userId = UUID.randomUUID();
        User existing = new User("keepname");
        existing.setId(userId);

        UpdateUserDto dto = new UpdateUserDto();
        dto.setUserId(userId);
        dto.setPassword("new-secret");

        when(userRepository.getById(userId)).thenReturn(existing);
        when(passwordEncoder.encode("new-secret")).thenReturn("new-hash");

        User result = userService.update(dto);

        assertEquals("keepname", result.getUsername());
        assertEquals("new-hash", result.getPassword());

        verify(userRepository, never()).getByUserName(any());
        verify(userRepository).updateUser(existing);
    }

    @Test
    void update_WhenUsernameTakenByAnotherUser_ShouldThrowConflict() {
        UUID userId = UUID.randomUUID();
        User existing = new User("oldname");
        existing.setId(userId);

        User other = new User("taken");
        other.setId(UUID.randomUUID());

        UpdateUserDto dto = new UpdateUserDto();
        dto.setUserId(userId);
        dto.setUsername("taken");

        when(userRepository.getById(userId)).thenReturn(existing);
        when(userRepository.getByUserName("taken")).thenReturn(other);

        assertThrows(ConflictException.class, () -> userService.update(dto));

        verify(userRepository, never()).updateUser(any());
    }

    @Test
    void update_WhenUserDoesNotExist_ShouldThrowNotFound() {
        UUID userId = UUID.randomUUID();

        UpdateUserDto dto = new UpdateUserDto();
        dto.setUserId(userId);
        dto.setUsername("newname");

        when(userRepository.getById(userId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.update(dto));

        verify(userRepository, never()).updateUser(any());
    }
}
