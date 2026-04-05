CREATE TABLE IF NOT EXISTS game
(
    game_id INT PRIMARY KEY AUTO_INCREMENT,
    turn    CHAR(3)     NOT NULL,
    status  VARCHAR(20) NOT NULL DEFAULT 'PLAYING'
);

CREATE TABLE IF NOT EXISTS piece
(
    piece_id   INT PRIMARY KEY AUTO_INCREMENT,
    game_id    INT         NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    team       CHAR(3)     NOT NULL,
    row_idx    INT         NOT NULL,
    col_idx    INT         NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (game_id)
);
