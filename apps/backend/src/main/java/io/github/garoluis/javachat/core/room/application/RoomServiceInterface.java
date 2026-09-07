package io.github.garoluis.javachat.core.room.application;

import io.github.garoluis.javachat.core.room.application.dto.CreateRoomDto;
import io.github.garoluis.javachat.core.room.application.dto.DeleteRoomDto;
import io.github.garoluis.javachat.core.room.application.dto.GetRoomsDto;
import io.github.garoluis.javachat.core.room.domain.Room;

import java.util.List;

public interface RoomServiceInterface {
    public Room create(CreateRoomDto dto);

    public void delete(DeleteRoomDto dto);

    public List<Room> get(GetRoomsDto dto);
}
