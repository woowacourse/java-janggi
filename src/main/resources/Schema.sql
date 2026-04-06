CREATE TABLE IF NOT EXISTS game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status ENUM('PLAYING', 'FINISHED') NOT NULL,
    current_turn ENUM('CHO', 'HAN') NOT NULL
    );

CREATE TABLE IF NOT EXISTS `piece` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `game_id` INT NOT NULL,
    `type` ENUM('CHA', 'MA', 'SANG', 'SA', 'GENERAL', 'PHO', 'BYEONG') NOT NULL,
    `team` ENUM('CHO', 'HAN') NOT NULL,
    `row_idx` INT NOT NULL,
    `col_idx` INT NOT NULL,
    CONSTRAINT `fk_piece_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`) ON DELETE CASCADE,
    UNIQUE KEY `unique_position` (`game_id`, `row_idx`, `col_idx`)
    );
