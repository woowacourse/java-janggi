USE
    janggi;

CREATE TABLE JanggiBoard
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    game_id    INT        NOT NULL,
    x_pos      INT        NOT NULL,
    y_pos      INT        NOT NULL,
    team_name  VARCHAR(1) NOT NULL,
    piece_type VARCHAR(1) NOT NULL,
    turn       BOOLEAN    NOT NULL
);
