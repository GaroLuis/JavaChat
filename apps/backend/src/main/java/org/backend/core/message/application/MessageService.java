package org.backend.core.message.application;

import org.backend.core.common.domain.exception.NotFoundException;
import org.backend.core.message.application.dto.CreateMessageDto;
import org.backend.core.message.application.dto.GetMessagesByRoomDto;
import org.backend.core.message.domain.Message;
import org.backend.core.message.domain.MessageRepositoryInterface;
import org.backend.core.room.domain.Room;
import org.backend.core.room.domain.RoomRepositoryInterface;
import org.backend.core.user.domain.User;
import org.backend.core.user.domain.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements MessageServiceInterface{
    private final UserRepositoryInterface userRepository;
    private final RoomRepositoryInterface roomRepository;
    private final MessageRepositoryInterface messageRepository;

    public MessageService(
            UserRepositoryInterface userRepository,
            RoomRepositoryInterface roomRepository,
            MessageRepositoryInterface messageRepository
    ) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Message create(CreateMessageDto dto) {
        User sender = userRepository.getById(dto.getSenderId());
        Room room = roomRepository.getById(dto.getRoomId());

        Message message = new Message(dto.getContent(), sender, room);

        return messageRepository.create(message);
    }

    @Override
    public List<Message> getMessagesByRoom(GetMessagesByRoomDto dto) {
        Room room = roomRepository.getById(dto.getRoomId());

        if (null == room) {
            throw new NotFoundException("Room not found");
        }

        return messageRepository.getByRoom(room);
    }
}
