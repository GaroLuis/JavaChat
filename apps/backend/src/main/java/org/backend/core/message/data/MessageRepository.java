package org.backend.core.message.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.backend.core.message.domain.Message;
import org.backend.core.message.domain.MessageRepositoryInterface;
import org.backend.core.room.data.RoomEntity;
import org.backend.core.room.data.RoomEntity_;
import org.backend.core.room.domain.Room;
import org.backend.core.user.data.UserEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

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
    public List<Message> getByRoom(Room room, @Nullable LocalDateTime cursor, int size) {
        var builder = entityManager.getCriteriaBuilder();
        Predicate where = builder.conjunction();
        var bq = builder.createQuery(MessageEntity.class);

        Root<MessageEntity> messageRoot = bq.from(MessageEntity.class);
        Fetch<MessageEntity, RoomEntity> roomFetch = messageRoot.fetch(MessageEntity_.ROOM, JoinType.INNER);
        Join<MessageEntity, RoomEntity> roomJoin = (Join<MessageEntity, RoomEntity>) roomFetch;

        where = builder.and(where, roomJoin.get(RoomEntity_.ID).equalTo(room.getId()));

        if (cursor != null) {
            where = builder.and(where, builder.lessThan(messageRoot.get(MessageEntity_.TIMESTAMP), cursor));
        }

        bq
                .select(messageRoot)
                .where(where)
                .orderBy(builder.desc(messageRoot.get(MessageEntity_.TIMESTAMP)));
        
        
        var messageQuery = entityManager.createQuery(bq);
        messageQuery.setMaxResults(size);

        var messageEntities = messageQuery.getResultList();
        return messageEntities.stream()
                .map(MessageEntity::toDomain)
                .toList();
    }
}
