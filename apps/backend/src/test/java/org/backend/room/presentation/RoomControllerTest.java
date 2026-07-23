package org.backend.room.presentation;

import jakarta.validation.Validator;
import org.backend.config.security.SessionUser;
import org.backend.core.room.application.RoomServiceInterface;
import org.backend.core.room.application.dto.CreateRoomDto;
import org.backend.core.room.domain.Room;
import org.backend.core.room.presentation.RoomController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    @Mock
    private RoomServiceInterface roomService;

    @Mock
    private Validator validator;

    @InjectMocks
    private RoomController roomController;

    private MockMvc mockMvc;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roomController)
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
    void create_ShouldReturnCreated() throws Exception {
        String json = """
                {"userIds":["%s","%s"]}
                """.formatted(UUID.randomUUID(), UUID.randomUUID());

        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(roomService).create(any(CreateRoomDto.class));
    }

    @Test
    void delete_ShouldReturnOk() throws Exception {
        UUID roomId = UUID.randomUUID();

        mockMvc.perform(delete("/rooms/{id}", roomId))
                .andExpect(status().isOk());

        verify(roomService).delete(any());
    }

    @Test
    void get_ShouldReturnRooms() throws Exception {
        Room room1 = new Room();
        Room room2 = new Room();
        when(roomService.get(any())).thenReturn(List.of(room1, room2));
        when(validator.validate(any())).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());

        verify(roomService).get(any());
    }
}
