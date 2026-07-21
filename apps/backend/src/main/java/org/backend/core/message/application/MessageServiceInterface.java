package org.backend.core.message.application;

import org.backend.core.message.application.dto.CreateMessageDto;
import org.backend.core.message.application.dto.GetMessagesByRoomDto;
import org.backend.core.message.domain.Message;

import java.util.List;

public interface MessageServiceInterface {
    public Message create(CreateMessageDto dto);

    public List<Message> getMessagesByRoom(GetMessagesByRoomDto dto);
}
