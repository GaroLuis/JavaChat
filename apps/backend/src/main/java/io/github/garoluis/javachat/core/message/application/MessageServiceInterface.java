package io.github.garoluis.javachat.core.message.application;

import io.github.garoluis.javachat.core.message.application.dto.CreateMessageDto;
import io.github.garoluis.javachat.core.message.application.dto.GetMessagesByRoomDto;
import io.github.garoluis.javachat.core.message.domain.Message;

import java.util.List;

public interface MessageServiceInterface {
    public Message create(CreateMessageDto dto);

    public List<Message> getMessagesByRoom(GetMessagesByRoomDto dto);
}
