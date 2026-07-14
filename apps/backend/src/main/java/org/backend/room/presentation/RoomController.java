package org.backend.room.presentation;

import org.backend.config.security.SessionUser;
import org.backend.room.application.RoomServiceInterface;
import org.backend.room.application.dto.CreateRoomDto;
import org.backend.room.application.dto.DeleteRoomDto;
import org.backend.room.application.dto.GetRoomsDto;
import org.backend.room.domain.Room;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
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
    public void create(@RequestBody CreateRoomDto dto) {
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
    public Set<Room> get(@AuthenticationPrincipal SessionUser principal) {
        GetRoomsDto dto = new GetRoomsDto();
        dto.setUserId(principal.id());

        return roomService.get(dto);
    }
}
