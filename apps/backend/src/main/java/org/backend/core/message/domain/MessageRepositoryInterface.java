package org.backend.core.message.domain;

import org.backend.core.room.domain.Room;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepositoryInterface {
    public Message create(Message message);

    public List<Message> getByRoom(Room room, @Nullable LocalDateTime cursor, int size);
}
