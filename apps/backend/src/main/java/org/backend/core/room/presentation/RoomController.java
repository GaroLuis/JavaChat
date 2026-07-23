package org.backend.core.room.presentation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.backend.config.security.SessionUser;
import org.backend.core.room.application.RoomServiceInterface;
import org.backend.core.room.application.dto.CreateRoomDto;
import org.backend.core.room.application.dto.DeleteRoomDto;
import org.backend.core.room.application.dto.GetRoomsDto;
import org.backend.core.room.presentation.mapper.RoomMapper;
import org.backend.core.room.presentation.mapper.RoomResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomServiceInterface roomService;

    private final Validator validator;

    public RoomController(
            RoomServiceInterface roomService,
            Validator validator
    ) {
        this.roomService = roomService;
        this.validator = validator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateRoomDto dto, @AuthenticationPrincipal SessionUser principal) {
        dto.addUserId(principal.id());

        var validations = validator.validate(dto);
        if (!validations.isEmpty()) {
            throw new ConstraintViolationException(validations);
        }

        roomService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, @AuthenticationPrincipal SessionUser principal) {
        DeleteRoomDto dto = new DeleteRoomDto();
        dto.setRoomId(id);
        dto.setUserId(principal.id());

        var validations = validator.validate(dto);
        if (!validations.isEmpty()) {
            throw new ConstraintViolationException(validations);
        }

        roomService.delete(dto);
    }

    @GetMapping
    public List<RoomResponseDto> getRooms(@AuthenticationPrincipal SessionUser principal) {
        GetRoomsDto dto = new GetRoomsDto();
        dto.setUserId(principal.id());

        var validations = validator.validate(dto);
        if (!validations.isEmpty()) {
            throw new ConstraintViolationException(validations);
        }

        return roomService.get(dto).stream()
                .map(RoomMapper::toResponseDto)
                .toList();
    }
}
