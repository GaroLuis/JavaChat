package org.backend.core.message.presentation;

import org.backend.config.security.SessionUser;
import org.backend.core.message.application.MessageServiceInterface;
import org.backend.core.message.application.dto.CreateMessageDto;
import org.backend.core.message.domain.Message;
import org.backend.core.user.domain.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final MessageServiceInterface messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageServiceInterface messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send-message")
    public void sendMessage(@Payload CreateMessageDto dto, @AuthenticationPrincipal SessionUser principal) {
        dto.setSenderId(principal.id());

        Message message = messageService.create(dto);

        message.getRoom().getUsers().forEach(user -> {
            messagingTemplate.convertAndSendToUser(
                    user.getId().toString(),
                    "/queue/messages",
                    message
            );
        });
    }
}
