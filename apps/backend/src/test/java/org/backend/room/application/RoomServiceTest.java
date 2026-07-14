package org.backend.room.application;

import org.backend.room.application.dto.CreateRoomDto;
import org.backend.room.application.dto.DeleteRoomDto;
import org.backend.room.application.dto.GetRoomsDto;
import org.backend.room.domain.Room;
import org.backend.room.domain.RoomRepositoryInterface;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepositoryInterface roomRepository;

    @Mock
    private UserRepositoryInterface userRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    void create_WhenAllUsersExist_ShouldCreateAndReturnRoom() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        CreateRoomDto dto = new CreateRoomDto();
        dto.setUserIds(List.of(userId1, userId2));

        User user1 = new User("user1");
        user1.setId(userId1);
        User user2 = new User("user2");
        user2.setId(userId2);

        when(userRepository.getByIds(dto.getUserIds())).thenReturn(Set.of(user1, user2));
        when(roomRepository.create(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Room result = roomService.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(2, result.getUsers().size());

        verify(userRepository).getByIds(dto.getUserIds());
        verify(roomRepository).create(any(Room.class));
    }

    @Test
    void create_WhenSomeUsersMissing_ShouldThrowException() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        CreateRoomDto dto = new CreateRoomDto();
        dto.setUserIds(List.of(userId1, userId2));

        User user1 = new User("user1");
        user1.setId(userId1);

        when(userRepository.getByIds(dto.getUserIds())).thenReturn(Set.of(user1));

        assertThrows(RuntimeException.class, () -> roomService.create(dto));
        verify(roomRepository, never()).create(any());
    }

    @Test
    void delete_WhenRoomExists_ShouldDeleteRoom() {
        UUID roomId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        DeleteRoomDto dto = new DeleteRoomDto();
        dto.setRoomId(roomId);
        dto.setUserId(userId);

        Room room = new Room();
        room.setId(roomId);

        when(roomRepository.getById(roomId)).thenReturn(room);

        roomService.delete(dto);

        verify(roomRepository).getById(roomId);
        verify(roomRepository).delete(room);
    }

    @Test
    void delete_WhenRoomDoesNotExist_ShouldThrowException() {
        UUID roomId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        DeleteRoomDto dto = new DeleteRoomDto();
        dto.setRoomId(roomId);
        dto.setUserId(userId);

        when(roomRepository.getById(roomId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> roomService.delete(dto));
        verify(roomRepository, never()).delete(any());
    }

    @Test
    void get_ShouldReturnRoomsForUser() {
        UUID userId = UUID.randomUUID();
        GetRoomsDto dto = new GetRoomsDto();
        dto.setUserId(userId);

        Room room1 = new Room();
        Room room2 = new Room();
        Set<Room> expectedRooms = Set.of(room1, room2);

        when(roomRepository.getByUser(userId)).thenReturn(expectedRooms);

        Set<Room> result = roomService.get(dto);

        assertEquals(expectedRooms, result);
        verify(roomRepository).getByUser(userId);
    }
}
