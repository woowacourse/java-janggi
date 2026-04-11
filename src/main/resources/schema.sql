CREATE TABLE IF NOT EXISTS game
(
    id           int auto_increment primary key,
    game_name    varchar(20) not null,
    current_turn varchar(5)  not null
);

CREATE TABLE IF NOT EXISTS board_piece
(
    id         int auto_increment primary key,
    game_id    int         not null,
    type       varchar(20) not null,
    team       varchar(10) not null,
    x_position int         not null,
    y_position int         not null,

    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game (id) ON DELETE CASCADE
);