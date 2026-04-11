CREATE DATABASE IF NOT EXISTS janggi_db;
USE janggi_db;

DROP TABLE IF EXISTS `board`;
CREATE TABLE `board`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `row_index`  INT         NOT NULL,
    `col_index`  INT         NOT NULL,
    `team`       VARCHAR(20) NOT NULL,
    `piece_type` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `game_state`;
CREATE TABLE `game_state`
(
    `id`           INT AUTO_INCREMENT PRIMARY KEY,
    `current_turn` VARCHAR(20) NOT NULL,
    `is_finished`  BOOLEAN DEFAULT FALSE,
    `cho_score`    DOUBLE  DEFAULT 0,
    `han_score`    DOUBLE  DEFAULT 0
);