CREATE TABLE IF NOT EXISTS game_room
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    turn VARCHAR(20) NOT NULL,

    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS piece
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    row_index      INT          NOT NULL,
    column_index   INT          NOT NULL,
    piece_type     VARCHAR(20)  NOT NULL,
    team           VARCHAR(20)  NOT NULL,
    game_room_name VARCHAR(100) NOT NULL,

    UNIQUE (row_index, column_index, game_room_name),

    FOREIGN KEY (game_room_name) REFERENCES game_room (name)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
