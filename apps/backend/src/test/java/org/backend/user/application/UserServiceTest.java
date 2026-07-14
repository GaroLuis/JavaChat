package org.backend.user.application;

import org.backend.user.application.dto.GetUsersDto;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

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
        GetUsersDto dto = new GetUsersDto();
        dto.setInput(input);

        User user1 = new User("john_doe");
        User user2 = new User("johnny");
        Set<User> expectedUsers = Set.of(user1, user2);

        when(userRepository.getByUserName(input, false)).thenReturn(expectedUsers);

        Set<User> result = userService.get(dto);

        assertEquals(expectedUsers, result);
        verify(userRepository).getByUserName(input, false);
    }
}
