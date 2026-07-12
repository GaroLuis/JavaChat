package org.backend.room.domain;

import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface RoomRepositoryInterface {
    public Room create(Room room);

    public void delete(Room room);

    public Set<Room> getByUser(UUID userId);

    public @Nullable Room getById(UUID id);
}
