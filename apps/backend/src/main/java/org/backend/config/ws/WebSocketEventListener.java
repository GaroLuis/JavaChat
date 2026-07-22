package org.backend.config.ws;

import org.backend.config.security.SessionUser;
import org.backend.core.user.application.UserServiceInterface;
import org.backend.core.user.application.dto.UpdateUserConnectionStatusDto;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final UserServiceInterface userService;

    public WebSocketEventListener(
            UserServiceInterface userService
    ) {
        this.userService = userService;
    }

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        var user = (SessionUser) event.getUser();

        if (null == user) {
            return;
        }

        var dto = new UpdateUserConnectionStatusDto(user.id(), true);
        userService.updateUserConnectionStatus(dto);

    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        var user = (SessionUser) event.getUser();

        if (null == user) {
            return;
        }

        var dto = new UpdateUserConnectionStatusDto(user.id(), false);
        userService.updateUserConnectionStatus(dto);
    }
}
