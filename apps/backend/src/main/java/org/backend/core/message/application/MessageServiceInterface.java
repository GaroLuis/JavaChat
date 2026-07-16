package org.backend.core.message.application;

import org.backend.core.message.application.dto.CreateMessageDto;
import org.backend.core.message.domain.Message;

public interface MessageServiceInterface {
    public Message create(CreateMessageDto dto);
}
