CREATE DATABASE IF NOT EXISTS `janggi` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `janggi`;

CREATE TABLE `team` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(10),
  `current` TINYINT(1)
);

CREATE TABLE `piece_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(10)
);

CREATE TABLE `piece` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `team_id` int unsigned,
  `piece_type_id` int unsigned,
  `x` int(2) unsigned,
  `y` int(2) unsigned,
  foreign key (`team_id`) references `team`(`id`),
  foreign key (`piece_type_id`) references `piece_type`(`id`)
);

CREATE DATABASE IF NOT EXISTS `janggi_test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `janggi_test`;

CREATE TABLE `team` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(10),
  `current` TINYINT(1)
);

CREATE TABLE `piece_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(10)
);

CREATE TABLE `piece` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `team_id` int unsigned,
  `piece_type_id` int unsigned,
  `x` int(2) unsigned,
  `y` int(2) unsigned,
  foreign key (`team_id`) references `team`(`id`),
  foreign key (`piece_type_id`) references `piece_type`(`id`)
);

