package org.backend.user.presentation;

import org.backend.config.security.SessionUser;
import org.backend.user.application.UserServiceInterface;
import org.backend.user.application.dto.GetUsersDto;
import org.backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceInterface userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        SessionUser principal = new SessionUser(UUID.randomUUID(), "testuser");
        var auth = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void get_WithSearchParam_ShouldReturnUsers() throws Exception {
        User user1 = new User("john_doe");
        User user2 = new User("johnny");
        when(userService.get(any(GetUsersDto.class))).thenReturn(Set.of(user1, user2));

        mockMvc.perform(get("/users").param("s", "john"))
                .andExpect(status().isOk());
    }
}
