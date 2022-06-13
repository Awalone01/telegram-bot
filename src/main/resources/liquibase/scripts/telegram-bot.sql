-- liquibase formatted sql

-- changeset avolkov:1
CREATE TABLE Notification (
    id                  serial NOT_NULL PRIMARY_KEY,
    chat_id             bigint NOT_NULL,
    notification_text   text NOT_NULL,
    notification_date   TIMESTAMP NOT_NULL
);