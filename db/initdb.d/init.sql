-- 데이터베이스 선택
USE janggi;

DROP TABLE IF EXISTS `piece`;
DROP TABLE IF EXISTS `janggi_game`;

CREATE TABLE `janggi_game` (
    `game_id` INT AUTO_INCREMENT NOT NULL,
    `playing_side` enum('HAN','CHO') NOT NULL,
    PRIMARY KEY (`game_id`)
);

CREATE TABLE `piece` (
    `piece_id` INT AUTO_INCREMENT NOT NULL,
    `piece_type` enum('GUNG','SA','MA','SANG','CHA','PO','JOL','BYEONG') NOT NULL,
    `side` enum('HAN','CHO') NOT NULL,
    `row_index` INT NOT NULL,
    `col_index` INT NOT NULL,
    `game_id` INT NOT NULL,
    PRIMARY KEY (`piece_id`),

    CONSTRAINT `FK_janggi_game_TO_piece_1` FOREIGN KEY (`game_id`)
        REFERENCES `janggi_game` (`game_id`) ON DELETE CASCADE,

    CONSTRAINT `UK_piece_position` UNIQUE (`game_id`, `row_index`, `col_index`)
);
