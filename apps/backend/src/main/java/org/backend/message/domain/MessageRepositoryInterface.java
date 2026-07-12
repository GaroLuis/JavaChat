package org.backend.message.domain;

import org.backend.message.data.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepositoryInterface {
    public Message create(Message message);
}
