package org.backend.room.presentation;

import org.backend.config.security.SessionUser;
import org.backend.room.application.RoomServiceInterface;
import org.backend.room.application.dto.CreateRoomDto;
import org.backend.room.domain.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Set;
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

    @InjectMocks
    private RoomController roomController;

    private MockMvc mockMvc;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();

        SessionUser principal = new SessionUser(userId, "testuser");
        var auth = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void create_ShouldReturnCreated() throws Exception {
        CreateRoomDto dto = new CreateRoomDto();
        dto.setUserIds(List.of(UUID.randomUUID(), UUID.randomUUID()));

        String json = """
                {"userIds":["%s","%s"]}
                """.formatted(dto.getUserIds().get(0), dto.getUserIds().get(1));

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
        when(roomService.get(any())).thenReturn(Set.of(room1, room2));

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());
    }
}
