-- liquibase formatted sql

-- changeset gordey_dovydenko:1

CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id                   VARCHAR(255) PRIMARY KEY,
    date_birth           DATE    NOT NULL,
    delivery_date_before INTEGER NOT NULL,
    affiliate_id         VARCHAR(255),
    online_status        BOOLEAN NOT NULL
);
-- rollback DROP TABLE users;

-- changeset gordey_dovydenko:2
CREATE TABLE affiliate
(
    id      VARCHAR(255) PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);
-- rollback DROP TABLE affiliate;

-- changeset gordey_dovydenko:3
CREATE TABLE command
(
    id          VARCHAR(255) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);
-- rollback DROP TABLE command;

-- changeset gordey_dovydenko:4
CREATE TABLE user_command
(
    user_id    VARCHAR(255) NOT NULL,
    command_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, command_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (command_id) REFERENCES command (id)
);
-- rollback DROP TABLE user_command;

-- changeset gordey_dovydenko:5
CREATE TABLE wishlist
(
    id            VARCHAR(255) PRIMARY KEY,
    user_id       VARCHAR(255) NOT NULL,
    raised        DECIMAL      NOT NULL,
    needed        DECIMAL      NOT NULL,
    is_active     BOOLEAN      NOT NULL,
    donation_link VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
-- rollback DROP TABLE wishlist;

-- changeset gordey_dovydenko:6
CREATE TABLE wishlist_item
(
    id          VARCHAR(255) PRIMARY KEY,
    wishlist_id VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    price       DECIMAL,
    comment     VARCHAR(255),
    rating      INTEGER      NOT NULL,
    link        VARCHAR(255),
    is_closed   BOOLEAN      NOT NULL,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist (id)
);
-- rollback DROP TABLE wishlist_item;

-- changeset gordey_dovydenko:7
CREATE TABLE wishlist_item_photo
(
    id               VARCHAR(255) PRIMARY KEY,
    wishlist_item_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (wishlist_item_id) REFERENCES wishlist_item (id)
);
-- rollback DROP TABLE wishlist_item_photo;

-- changeset t9404:8
CREATE TABLE chat_room
(
    chat_room_id VARCHAR(255) PRIMARY KEY,
    name         VARCHAR(255),
    description  VARCHAR(255) NOT NULL
);
-- rollback DROP TABLE chat_room;

-- changeset t9404:9
CREATE TABLE IF NOT EXISTS message
(
    message_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_room_id VARCHAR(255) NOT NULL,
    sender_id    VARCHAR(255) NOT NULL,
    content      VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (chat_room_id),
    FOREIGN KEY (sender_id) REFERENCES users (id)
);
-- rollback DROP TABLE message;

-- changeset t9404:10

CREATE TABLE IF NOT EXISTS subscribe
(
    owner_id      VARCHAR(255) NOT NULL,
    subscriber_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (owner_id, subscriber_id),
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (subscriber_id) REFERENCES users (id)
);

-- rollback DROP TABLE subscribe;