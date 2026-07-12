package org.backend.message.application;

import org.backend.message.application.dto.CreateMessageDto;
import org.backend.message.domain.Message;
import org.backend.message.domain.MessageRepositoryInterface;
import org.backend.room.domain.Room;
import org.backend.room.domain.RoomRepositoryInterface;
import org.backend.user.domain.User;
import org.backend.user.domain.UserRepositoryInterface;
import org.springframework.stereotype.Service;

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
}
