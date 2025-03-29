CREATE DATABASE IF NOT EXISTS `janggi` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `janggi`;

CREATE TABLE `Team` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(10),
  `current` TINYINT(1)
);

CREATE TABLE `PieceType` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(10)
);

CREATE TABLE `Piece` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `teamId` int unsigned,
  `pieceTypeId` int unsigned,
  `x` int(2) unsigned,
  `y` int(2) unsigned,
  foreign key (`teamId`) references `Team`(`id`),
  foreign key (`pieceTypeId`) references `PieceType`(`id`)
);
