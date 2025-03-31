USE janggi;

CREATE TABLE IF NOT EXISTS board_status
(
    piece_id     INT AUTO_INCREMENT,
    piece_name   VARCHAR(20),
    team_name    VARCHAR(10),
    piece_status VARCHAR(10),
    position_x   INT,
    position_y   INT,
    PRIMARY KEY (piece_id, position_x, position_y)
);
