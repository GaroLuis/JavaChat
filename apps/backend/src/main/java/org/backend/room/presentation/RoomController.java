package org.backend.room.presentation;

import org.backend.room.application.RoomServiceInterface;
import org.backend.room.application.dto.CreateRoomDto;
import org.backend.room.application.dto.DeleteRoomDto;
import org.backend.room.application.dto.GetRoomsDto;
import org.backend.room.domain.Room;
import org.springframework.http.HttpStatus;
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
    public void delete(@PathVariable UUID id) {
        DeleteRoomDto dto = new DeleteRoomDto();
        dto.setRoomId(id);

        roomService.delete(dto);
    }

    @GetMapping
    public Set<Room> get() {
        GetRoomsDto dto = new GetRoomsDto();

        return roomService.get(dto);
    }
}
