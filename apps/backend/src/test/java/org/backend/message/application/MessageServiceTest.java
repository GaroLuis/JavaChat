package org.backend.message.application;

import org.backend.message.application.dto.CreateMessageDto;
import org.backend.message.domain.Message;
import org.backend.message.domain.MessageRepositoryInterface;
import org.backend.room.domain.Room;
import org.backend.room.domain.RoomRepositoryInterface;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private UserRepositoryInterface userRepository;

    @Mock
    private RoomRepositoryInterface roomRepository;

    @Mock
    private MessageRepositoryInterface messageRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    void create_WithValidData_ShouldCreateAndReturnMessage() {
        UUID senderId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();
        String content = "Hello, World!";

        CreateMessageDto dto = new CreateMessageDto();
        dto.setSenderId(senderId);
        dto.setRoomId(roomId);
        dto.setContent(content);

        User sender = new User("sender");
        sender.setId(senderId);
        Room room = new Room();
        room.setId(roomId);

        when(userRepository.getById(senderId)).thenReturn(sender);
        when(roomRepository.getById(roomId)).thenReturn(room);
        when(messageRepository.create(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Message result = messageService.create(dto);

        assertNotNull(result);
        assertEquals(content, result.getContent());
        assertEquals(sender, result.getSender());
        assertEquals(room, result.getRoom());
        assertNotNull(result.getId());
        assertNotNull(result.getTimestamp());

        verify(userRepository).getById(senderId);
        verify(roomRepository).getById(roomId);
        verify(messageRepository).create(any(Message.class));
    }
}
