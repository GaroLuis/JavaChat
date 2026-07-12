package org.backend.message.data;

import jakarta.persistence.EntityManager;
import org.backend.message.domain.Message;
import org.backend.message.domain.MessageRepositoryInterface;
import org.backend.room.data.RoomEntity;
import org.backend.user.data.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository implements MessageRepositoryInterface {
    private final EntityManager entityManager;

    public MessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Message create(Message message) {
        MessageEntity entity = new MessageEntity();

        entity.setId(message.getId());
        entity.setContent(message.getContent());
        entity.setTimestamp(message.getTimestamp());

        UserEntity sender = entityManager.getReference(UserEntity.class, message.getSender().getId());
        entity.setSender(sender);
        RoomEntity room = entityManager.getReference(RoomEntity.class, message.getRoom().getId());
        entity.setRoom(room);

        entityManager.persist(entity);
        entityManager.flush();

        return message;
    }
}
