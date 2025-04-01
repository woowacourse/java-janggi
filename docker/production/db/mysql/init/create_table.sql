CREATE TABLE IF NOT EXISTS game_room
(
    name VARCHAR(100) PRIMARY KEY,
    turn VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS piece
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    row_index    INT          NOT NULL,
    column_index INT          NOT NULL,
    piece_type   VARCHAR(20)  NOT NULL,
    team         VARCHAR(20)  NOT NULL,
    game_room    VARCHAR(100) NOT NULL,

    UNIQUE (row_index, column_index, game_room),

    FOREIGN KEY (game_room) REFERENCES game_room (name)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
