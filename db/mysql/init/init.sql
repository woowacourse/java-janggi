USE
janggi;

CREATE TABLE GameRoom
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    turn_color   VARCHAR(10) NOT NULL,
    start_time   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_finished  BOOLEAN     NOT NULL DEFAULT FALSE,
    winner       VARCHAR(10)          DEFAULT NULL,
    end_time     TIMESTAMP            DEFAULT NULL,
    red_score    INT                  DEFAULT 0,
    blue_score   INT                  DEFAULT 0,
);

CREATE TABLE BoardPiece
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    gameroom_id  INT         NOT NULL,
    position_row INT         NOT NULL,
    position_col INT         NOT NULL,
    piece_type   VARCHAR(50) NOT NULL,
    piece_color  VARCHAR(10) NOT NULL,
    FOREIGN KEY (gameroom_id) REFERENCES GameRoom (id) ON DELETE CASCADE
);
