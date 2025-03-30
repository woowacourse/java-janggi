CREATE TABLE board
(
    board_id BIGINT      NOT NULL AUTO_INCREMENT,
    turn     VARCHAR(10) NOT NULL,
    PRIMARY KEY (board_id)
);

CREATE TABLE piece
(
    piece_id BIGINT NOT NULL AUTO_INCREMENT,
    x INT NOT NULL,
    y INT NOT NULL,
    team VARCHAR(10) NOT NULL,
    board_id BIGINT,
    PRIMARY KEY (piece_id),
    FOREIGN KEY (board_id) REFERENCES board (board_id)
);