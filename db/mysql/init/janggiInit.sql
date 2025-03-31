DROP TABLE IF EXISTS piece;
DROP TABLE IF EXISTS board;

CREATE TABLE IF NOT EXISTS board
(
    board_id VARCHAR(36) NOT NULL,
    turn     VARCHAR(10) NOT NULL,
    PRIMARY KEY (board_id)
);

CREATE TABLE IF NOT EXISTS piece
(
    piece_id VARCHAR(36) NOT NULL,
    x        INT         NOT NULL,
    y        INT         NOT NULL,
    team     VARCHAR(10) NOT NULL,
    piece_type    VARCHAR(255) NOT NULL,
    board_id VARCHAR(255),
    PRIMARY KEY (piece_id),
    FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE
);