package org.backend.core.message.domain;

import org.backend.core.room.domain.Room;

import java.util.List;

public interface MessageRepositoryInterface {
    public Message create(Message message);

    public List<Message> getByRoom(Room room);
}
