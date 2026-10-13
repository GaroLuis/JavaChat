package io.github.garoluis.javachat.user.presentation;

import jakarta.validation.Validator;
import io.github.garoluis.javachat.config.exception.GlobalHandlerException;
import io.github.garoluis.javachat.config.security.SessionUser;
import io.github.garoluis.javachat.core.common.domain.exception.ConflictException;
import io.github.garoluis.javachat.core.user.application.UserServiceInterface;
import io.github.garoluis.javachat.core.user.application.dto.CreateUserDto;
import io.github.garoluis.javachat.core.user.application.dto.GetUsersDto;
import io.github.garoluis.javachat.core.user.application.dto.UpdateUserDto;
import io.github.garoluis.javachat.core.user.domain.User;
import io.github.garoluis.javachat.core.user.presentation.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceInterface userService;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalHandlerException())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();

        SessionUser principal = new SessionUser(userId, "testuser");
        var auth = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void get_WithSearchParam_ShouldReturnUsers() throws Exception {
        User user1 = new User("john_doe");
        User user2 = new User("johnny");
        when(userService.getUsers(any(GetUsersDto.class))).thenReturn(List.of(user1, user2));
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/users").param("s", "john"))
                .andExpect(status().isOk());

        verify(userService).getUsers(any(GetUsersDto.class));
    }

    @Test
    void create_WithValidBody_ShouldReturnCreated() throws Exception {
        User user = new User("newuser");
        when(userService.create(any(CreateUserDto.class))).thenReturn(user);

        String json = """
                {"username":"newuser","password":"secret"}
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value("newuser"));

        ArgumentCaptor<CreateUserDto> captor = ArgumentCaptor.forClass(CreateUserDto.class);
        verify(userService).create(captor.capture());

        assertEquals("newuser", captor.getValue().getUsername());
        assertEquals("secret", captor.getValue().getPassword());
    }

    @Test
    void create_WithBlankUsername_ShouldReturnBadRequest() throws Exception {
        String json = """
                {"username":" ","password":"secret"}
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(userService, never()).create(any());
    }

    @Test
    void create_WhenUsernameTaken_ShouldReturnConflict() throws Exception {
        when(userService.create(any(CreateUserDto.class))).thenThrow(new ConflictException("Username already taken"));

        String json = """
                {"username":"alice","password":"secret"}
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void updateMe_WithValidBody_ShouldReturnOk() throws Exception {
        User user = new User("newname");
        user.setId(userId);
        when(userService.update(any(UpdateUserDto.class))).thenReturn(user);

        String json = """
                {"username":"newname","password":"secret"}
                """;

        mockMvc.perform(put("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newname"));

        ArgumentCaptor<UpdateUserDto> captor = ArgumentCaptor.forClass(UpdateUserDto.class);
        verify(userService).update(captor.capture());

        assertEquals(userId, captor.getValue().getUserId());
        assertEquals("newname", captor.getValue().getUsername());
        assertEquals("secret", captor.getValue().getPassword());
    }

    @Test
    void updateMe_WithBlankUsername_ShouldReturnBadRequest() throws Exception {
        String json = """
                {"username":"   "}
                """;

        mockMvc.perform(put("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(userService, never()).update(any());
    }
}
