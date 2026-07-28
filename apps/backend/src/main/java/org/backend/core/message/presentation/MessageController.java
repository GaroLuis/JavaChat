package org.backend.core.message.presentation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.backend.config.security.SessionUser;
import org.backend.core.message.application.MessageServiceInterface;
import org.backend.core.message.application.dto.GetMessagesByRoomDto;
import org.backend.core.message.presentation.mapper.MessageResponseDto;
import org.backend.core.message.presentation.mapper.MessageMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class MessageController {
    private final MessageServiceInterface messageService;

    private final Validator validator;

    public MessageController(MessageServiceInterface messageService, Validator validator) {
        this.messageService = messageService;
        this.validator = validator;
    }

    @GetMapping("/rooms/{id}/messages")
    public List<MessageResponseDto> getMessagesByRoom(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
            @AuthenticationPrincipal SessionUser principal
    ) {
        GetMessagesByRoomDto dto = new GetMessagesByRoomDto();
        dto.setUserId(principal.id());
        dto.setRoomId(id);
        dto.setSize(size);
        dto.setCursor(cursor);

        var validations = validator.validate(dto);
        if (!validations.isEmpty()) {
            throw new ConstraintViolationException(validations);
        }

        return messageService.getMessagesByRoom(dto).stream()
                .map(MessageMapper::toResponseDto)
                .toList();
    }
}
