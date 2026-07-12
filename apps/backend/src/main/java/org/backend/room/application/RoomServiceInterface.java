package org.backend.room.application;

import org.backend.room.application.dto.CreateRoomDto;
import org.backend.room.application.dto.DeleteRoomDto;
import org.backend.room.application.dto.GetRoomsDto;
import org.backend.room.domain.Room;

import java.util.Set;

public interface RoomServiceInterface {
    public Room create(CreateRoomDto dto);

    public void delete(DeleteRoomDto dto);

    public Set<Room> get(GetRoomsDto dto);
}
