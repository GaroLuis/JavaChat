package org.backend.core.room.presentation;

import org.backend.config.security.SessionUser;
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
@RequestMapping("/rooms")
public class RoomController {
    private final RoomServiceInterface roomService;

    public RoomController(RoomServiceInterface roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateRoomDto dto, @AuthenticationPrincipal SessionUser principal) {
        dto.addUserId(principal.id());

        roomService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, @AuthenticationPrincipal SessionUser principal) {
        DeleteRoomDto dto = new DeleteRoomDto();
        dto.setRoomId(id);
        dto.setUserId(principal.id());

        roomService.delete(dto);
    }

    @GetMapping
    public List<Room> getRooms(@AuthenticationPrincipal SessionUser principal) {
        GetRoomsDto dto = new GetRoomsDto();
        dto.setUserId(principal.id());

        return roomService.get(dto);
    }
}
