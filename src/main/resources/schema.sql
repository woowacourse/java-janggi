CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_cho VARCHAR(50)   NOT NULL,
    player_han VARCHAR(50)   NOT NULL,
    current_turn VARCHAR(10) NOT NULL,
    status BOOLEAN           NOT NULL
);

CREATE TABLE IF NOT EXISTS piece (
    board_id BIGINT      NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    side VARCHAR(10)       NOT NULL,
    pos_row INT            NOT NULL,
    pos_col INT            NOT NULL,

    PRIMARY KEY (board_id, pos_row, pos_col),
    FOREIGN KEY (board_id) REFERENCES game(id)
    ON DELETE CASCADE
);