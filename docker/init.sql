DROP DATABASE IF EXISTS janggi;

CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

DROP TABLE IF EXISTS piece;

CREATE TABLE piece
(
    piece_id     INT AUTO_INCREMENT PRIMARY KEY,
    x_coordinate INT        NOT NULL,
    y_coordinate INT        NOT NULL,
    piece_type   VARCHAR(6) NOT NULL,
    team         CHAR(3)    NOT NULL
);

DROP TABLE IF EXISTS turn;

CREATE TABLE turn
(
    team  CHAR(3) PRIMARY KEY DEFAULT 'CHO',
    round INT NOT NULL        DEFAULT 1
);

INSERT INTO turn (team, round)
VALUES ('CHO', 1);
