package io.github.garoluis.javachat.core.room.domain;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface RoomRepositoryInterface {
    public Room create(Room room);

    public void delete(Room room);

    public List<Room> getByUser(UUID userId);

    public @Nullable Room getById(UUID id);
}
