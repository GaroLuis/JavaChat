package org.backend.core.room.application;

import org.backend.core.common.domain.exception.NotFoundException;
import org.backend.core.room.application.dto.CreateRoomDto;
import org.backend.core.room.application.dto.DeleteRoomDto;
import org.backend.core.room.application.dto.GetRoomsDto;
import org.backend.core.room.domain.Room;
import org.backend.core.room.domain.RoomRepositoryInterface;
import org.backend.core.user.domain.User;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements RoomServiceInterface {
    private final RoomRepositoryInterface roomRepository;
    private final UserRepositoryInterface userRepository;

    public RoomService(
            RoomRepositoryInterface roomRepository,
            UserRepositoryInterface userRepository
    ) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Room create(CreateRoomDto dto) {
        Room room = new Room();

        List<User> users = userRepository.getByIds(dto.getUserIds());

        room.setUsers(users);

        return roomRepository.create(room);
    }

    @Override
    public void delete(DeleteRoomDto dto) {
        Room room = roomRepository.getById(dto.getRoomId());

        if (null == room) {
            throw new NotFoundException("Room not found");
        }

        //TODO: Validate that the dto.getUser is in room.getUsers

        roomRepository.delete(room);
    }

    @Override
    public List<Room> get(GetRoomsDto dto) {
        return roomRepository.getByUser(dto.getUserId());
    }
}
