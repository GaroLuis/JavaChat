package org.backend.core.message.presentation;

import org.backend.config.security.SessionUser;
import org.backend.core.message.application.MessageServiceInterface;
import org.backend.core.message.application.dto.CreateMessageDto;
import org.backend.core.message.domain.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final MessageServiceInterface messageService;

    public ChatController(MessageServiceInterface messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/send-message")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload CreateMessageDto dto, @AuthenticationPrincipal SessionUser principal) {
        dto.setSenderId(principal.id());

        return messageService.create(dto);
    }
}
