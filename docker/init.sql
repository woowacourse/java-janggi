DROP DATABASE IF EXISTS `janggi`;

CREATE DATABASE `janggi` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `janggi`;

DROP TABLE IF EXISTS `janggi`;

CREATE TABLE `janggi` (
    janggi_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	red_player_name VARCHAR(64) NOT NULL,
	green_player_name VARCHAR(64) NOT NULL,
	red_score FLOAT NOT NULL,
	green_score FLOAT NOT NULL,
	game_status VARCHAR(64) NOT NULL,
	game_turn VARCHAR(64) NOT NULL
);

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
    board_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    janggi_id INT NOT NULL,
    CONSTRAINT fk_janggi FOREIGN KEY (janggi_id) REFERENCES janggi(janggi_id)
);

DROP TABLE IF EXISTS `piece`;

CREATE TABLE `piece` (
    piece_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    board_id INT NOT NULL,
    piece_type VARCHAR(64) NOT NULL,
    team VARCHAR(64) NOT NULL,
    row_num INT NOT NULL,
    column_num INT NOT NULL,
    is_alive BOOLEAN NOT NULL,
    CONSTRAINT fk_board FOREIGN KEY (board_id) REFERENCES board(board_id)
);

