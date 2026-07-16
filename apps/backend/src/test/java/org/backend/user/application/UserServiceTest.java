package org.backend.user.application;

import org.backend.core.user.application.UserService;
import org.backend.core.user.application.dto.GetUsersDto;
import org.backend.core.user.domain.User;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryInterface userRepository;

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

        when(userRepository.getByUserName(input, false, List.of(userId))).thenReturn(expectedUsers);

        List<User> result = userService.getUsers(dto);

        assertEquals(expectedUsers, result);
        verify(userRepository).getByUserName(input, false, List.of(userId));
    }
}
