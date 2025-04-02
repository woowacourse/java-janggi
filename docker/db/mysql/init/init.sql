CREATE DATABASE IF NOT EXISTS janggi
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

CREATE TABLE IF NOT EXISTS `turn` (
    turn VARCHAR(3) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS `board` (
    board_id        INT NOT NULL,
	point_row       INT NOT NULL,
	point_column    INT NOT NULL,
	team            VARCHAR(3) NOT NULL,
	piece_type      VARCHAR(6) NOT NULL,
	PRIMARY KEY (board_id, point_row, point_column)
);
