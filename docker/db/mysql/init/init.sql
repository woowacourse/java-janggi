CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE testjanggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

DROP TABLE IF EXISTS `pieces`;

CREATE TABLE IF NOT EXISTS `pieces` (
	`name` VARCHAR(12) NOT NULL,
	`side` ENUM('CHO', 'HAN') NOT NULL,
	`position_row` INT NOT NULL,
	`position_column` INT NOT NULL,
	`last_moved` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`position_row`, `position_column`)
);

USE testjanggi;

DROP TABLE IF EXISTS `pieces`;

CREATE TABLE IF NOT EXISTS `pieces` (
	`name` VARCHAR(12) NOT NULL,
	`side` ENUM('CHO', 'HAN') NOT NULL,
	`position_row` INT NOT NULL,
	`position_column` INT NOT NULL,
	`last_moved` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`position_row`, `position_column`)
);
