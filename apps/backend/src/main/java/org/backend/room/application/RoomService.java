package org.backend.room.application;

import org.backend.room.application.dto.CreateRoomDto;
import org.backend.room.application.dto.DeleteRoomDto;
import org.backend.room.application.dto.GetRoomsDto;
import org.backend.room.domain.Room;
import org.backend.room.domain.RoomRepositoryInterface;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Set;

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

        Set<User> users = userRepository.getByIds(dto.getUserIds());

        if (users.size() != dto.getUserIds().size()) {
            throw new RuntimeException();
        }

        room.setUsers(users);

        return roomRepository.create(room);
    }

    @Override
    public void delete(DeleteRoomDto dto) {
        @Nullable Room room = roomRepository.getById(dto.getRoomId());

        if (null == room) {
            throw new RuntimeException();
        }

        //TODO: Validate that the dto.getUser is in room.getUsers

        roomRepository.delete(room);
    }

    @Override
    public Set<Room> get(GetRoomsDto dto) {
        return roomRepository.getByUser(dto.getUserId());
    }
}
