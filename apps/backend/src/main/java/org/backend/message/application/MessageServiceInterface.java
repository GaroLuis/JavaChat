package org.backend.message.application;

import org.backend.message.application.dto.CreateMessageDto;
import org.backend.message.domain.Message;

public interface MessageServiceInterface {
    public Message create(CreateMessageDto dto);
}
