CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE chat.users (
    id UUID NOT NULL,
    username VARCHAR(100) NOT NULL,
    connected BOOLEAN NOT NULL DEFAULT FALSE,
    last_connection TIMESTAMP,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_username UNIQUE (username)
);

CREATE TABLE chat.rooms (
    id UUID NOT NULL,
    CONSTRAINT pk_rooms PRIMARY KEY (id)
);

CREATE TABLE chat.messages (
    id UUID NOT NULL,
    content VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    sender_id UUID NOT NULL,
    room_id UUID NOT NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id),
    CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES chat.users(id),
    CONSTRAINT fk_messages_room FOREIGN KEY (room_id) REFERENCES chat.rooms(id)
);

CREATE TABLE chat.users_rooms (
    room_id UUID NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT pk_users_rooms PRIMARY KEY (room_id, user_id),
    CONSTRAINT fk_users_rooms_room FOREIGN KEY (room_id) REFERENCES chat.rooms(id),
    CONSTRAINT fk_users_rooms_user FOREIGN KEY (user_id) REFERENCES chat.users(id)
);
