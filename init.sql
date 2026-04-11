CREATE TABLE IF NOT EXISTS board (
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(10) NOT NULL,
    turn   VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS piece (
    id         INTEGER AUTO_INCREMENT PRIMARY KEY,
    board_id   BIGINT      NOT NULL,
    `row`        INTEGER     NOT NULL,
    `col`        INTEGER     NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    team       VARCHAR(10) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board(id)
);
