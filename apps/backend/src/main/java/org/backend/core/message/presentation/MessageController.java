package org.backend.core.message.presentation;

import org.backend.config.security.SessionUser;
import org.backend.core.message.application.MessageServiceInterface;
import org.backend.core.message.application.dto.GetMessagesByRoomDto;
import org.backend.core.message.domain.Message;
import org.backend.core.room.application.RoomServiceInterface;
import org.backend.core.room.application.dto.CreateRoomDto;
import org.backend.core.room.application.dto.DeleteRoomDto;
import org.backend.core.room.application.dto.GetRoomsDto;
import org.backend.core.room.domain.Room;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class MessageController {
    private final MessageServiceInterface messageService;

    public MessageController(MessageServiceInterface messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/rooms/{id}/messages")
    public List<Message> getMessagesByRoom(@PathVariable UUID id, @AuthenticationPrincipal SessionUser principal) {
        GetMessagesByRoomDto dto = new GetMessagesByRoomDto();
        dto.setUserId(principal.id());
        dto.setRoomId(id);

        return messageService.getMessagesByRoom(dto);
    }
}
