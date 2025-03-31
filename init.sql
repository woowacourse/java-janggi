CREATE DATABASE IF NOT EXISTS janggi;

USE janggi;

CREATE TABLE IF NOT EXISTS janggi_turn
(
    id    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    team  VARCHAR(30) NOT NULL,
    turn  INT         NOT NULL UNIQUE,
    score INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS piece
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    `row`          INT          NOT NULL,
    `column`       INT          NOT NULL,
    type           VARCHAR(255) NOT NULL,
    team           VARCHAR(255) NOT NULL,
    janggi_turn_fk INT          NOT NULL,
    FOREIGN KEY (janggi_turn_fk) REFERENCES janggi_turn (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY piece_janggi_turn_fk (janggi_turn_fk)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE DATABASE IF NOT EXISTS test_janggi;

USE test_janggi;

CREATE TABLE IF NOT EXISTS janggi_turn
(
    id    INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    team  VARCHAR(30) NOT NULL,
    turn  INT         NOT NULL UNIQUE,
    score INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS piece
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    `row`          INT          NOT NULL,
    `column`       INT          NOT NULL,
    type           VARCHAR(255) NOT NULL,
    team           VARCHAR(255) NOT NULL,
    janggi_turn_fk INT          NOT NULL,
    FOREIGN KEY (janggi_turn_fk) REFERENCES janggi_turn (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY piece_janggi_turn_fk (janggi_turn_fk)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
