package org.backend.core.message.data;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.backend.core.message.domain.Message;
import org.backend.core.message.domain.MessageRepositoryInterface;
import org.backend.core.room.data.RoomEntity;
import org.backend.core.room.domain.Room;
import org.backend.core.user.data.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageRepository implements MessageRepositoryInterface {
    private final EntityManager entityManager;

    public MessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
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

    @Override
    public List<Message> getByRoom(Room room) {
        var messageQuery = entityManager.createQuery(
                """
                            SELECT m
                            FROM MessageEntity m
                            JOIN m.room r
                            WHERE r.id = :roomId
                            ORDER BY m.timestamp
                        """, MessageEntity.class
        );
        messageQuery.setParameter("roomId", room.getId());
        var messageEntities = messageQuery.getResultStream();

        return messageEntities.map(MessageEntity::map).collect(Collectors.toList());
    }
}
