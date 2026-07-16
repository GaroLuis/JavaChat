package org.backend.core.room.application;

import org.backend.core.room.application.dto.CreateRoomDto;
import org.backend.core.room.application.dto.DeleteRoomDto;
import org.backend.core.room.application.dto.GetRoomsDto;
import org.backend.core.room.domain.Room;

import java.util.List;

public interface RoomServiceInterface {
    public Room create(CreateRoomDto dto);

    public void delete(DeleteRoomDto dto);

    public List<Room> get(GetRoomsDto dto);
}
