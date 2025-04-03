SET character_set_client = 'utf8';
SET character_set_connection = 'utf8';
SET character_set_results = 'utf8';

CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

DROP TABLE IF EXISTS `games`;
DROP TABLE IF EXISTS `pieces`;

CREATE TABLE `games` (
    `id` INT AUTO_INCREMENT,
    `current_turn` ENUM('CHO', 'HAN') NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE `pieces` (
    `id` INT AUTO_INCREMENT,
    `game_id` INT,
	`name` VARCHAR(12) NOT NULL,
	`side` ENUM('CHO', 'HAN') NOT NULL,
	`position_row` INT NOT NULL,
	`position_column` INT NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`game_id`) REFERENCES `games`(`id`)
);
