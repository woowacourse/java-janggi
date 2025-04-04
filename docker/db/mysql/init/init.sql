CREATE DATABASE janggi_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

CREATE TABLE `piece` (
                         `piece_id` int NOT NULL AUTO_INCREMENT,
                         `column` varchar(10) NOT NULL,
                         `row` varchar(10) NOT NULL,
                         `team` varchar(10) NOT NULL,
                         `type` varchar(10) NOT NULL,
                         PRIMARY KEY (`piece_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `game` (
                        `game_id` int NOT NULL DEFAULT '1',
                        `turn` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

USE janggi_test;

CREATE TABLE `piece` (
                         `piece_id` int NOT NULL AUTO_INCREMENT,
                         `column` varchar(10) NOT NULL,
                         `row` varchar(10) NOT NULL,
                         `team` varchar(10) NOT NULL,
                         `type` varchar(10) NOT NULL,
                         PRIMARY KEY (`piece_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `game` (
                        `game_id` int NOT NULL DEFAULT '1',
                        `turn` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
