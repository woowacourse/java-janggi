USE janggi;

CREATE TABLE IF NOT EXISTS game (
    `id` 			BIGINT PRIMARY KEY,
    `game_status` VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS turn (
    `id`                BIGINT PRIMARY KEY,
    `game_id`           BIGINT NOT NULL,
    `current_turn_team` VARCHAR(10) NOT NULL,
    `turn_status` VARCHAR(10) NOT NULL,
    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS piece (
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `turn_id`    BIGINT NOT NULL,
    `piece_type` VARCHAR(10) NOT NULL,
    `team_type`  VARCHAR(10) NOT NULL,
    `x`          INT NOT NULL,
    `y`          INT NOT NULL,
    CONSTRAINT fk_turn_id FOREIGN KEY (turn_id) REFERENCES turn(id) ON DELETE CASCADE
);