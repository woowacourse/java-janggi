USE janggi;

CREATE TABLE Board
(
    id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE GameRoom
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    board_id     INT         NOT NULL,
    turn_color   VARCHAR(10) NOT NULL,
    start_time   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_finished  BOOLEAN     NOT NULL DEFAULT FALSE,
    winner       VARCHAR(10)          DEFAULT NULL,
    end_time     TIMESTAMP            DEFAULT NULL,
    red_score    INT                  DEFAULT 0,
    blue_score   INT                  DEFAULT 0,
    FOREIGN KEY (board_id) REFERENCES Board (id) ON DELETE CASCADE
);

CREATE TABLE PiecePosition
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    board_id     INT         NOT NULL,
    position_row INT         NOT NULL,
    position_col INT         NOT NULL,
    piece_type   VARCHAR(50) NOT NULL,
    piece_color  VARCHAR(10) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES Board (id) ON DELETE CASCADE
);
