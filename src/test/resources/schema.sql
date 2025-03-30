DROP TABLE IF EXISTS pieces;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS janggi_games;

CREATE TABLE janggi_games
(
    game_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_status VARCHAR(20) NOT NULL,
    turn        VARCHAR(10) NOT NULL,
    undo_last   BOOLEAN     NOT NULL
);

CREATE TABLE pieces
(
    piece_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id         BIGINT      NOT NULL,
    position_row    INT         NOT NULL,
    position_column INT         NOT NULL,
    team_type       VARCHAR(10) NOT NULL,
    piece_type      VARCHAR(10) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES janggi_games (game_id)
);

CREATE TABLE players
(
    player_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id   BIGINT      NOT NULL,
    name      VARCHAR(50) NOT NULL,
    team_type VARCHAR(10) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES janggi_games (game_id)
);
