package io.github.garoluis.javachat.core.message.application;

import io.github.garoluis.javachat.core.common.domain.exception.NotFoundException;
import io.github.garoluis.javachat.core.message.application.dto.CreateMessageDto;
import io.github.garoluis.javachat.core.message.application.dto.GetMessagesByRoomDto;
import io.github.garoluis.javachat.core.message.domain.Message;
import io.github.garoluis.javachat.core.message.domain.MessageRepositoryInterface;
import io.github.garoluis.javachat.core.room.domain.Room;
import io.github.garoluis.javachat.core.room.domain.RoomRepositoryInterface;
import io.github.garoluis.javachat.core.user.domain.User;
import io.github.garoluis.javachat.core.user.domain.UserRepositoryInterface;
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

        return messageRepository.getByRoom(room, dto.getCursor(), dto.getSize());
    }
}
