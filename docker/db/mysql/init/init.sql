create user 'user'@'localhost' identified by 'password';

grant all privileges on *.* to 'user'@'localhost';

flush privileges;

CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

CREATE TABLE IF NOT EXISTS piece
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    row_value    INT         NOT NULL,
    column_value INT         NOT NULL,
    piece_type   VARCHAR(20) NOT NULL,
    team         VARCHAR(20) NOT NULL,

    UNIQUE (row_value, column_value)
);

CREATE TABLE IF NOT EXISTS turn
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    value INT NOT NULL
);
