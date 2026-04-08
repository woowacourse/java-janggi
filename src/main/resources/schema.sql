CREATE TABLE IF NOT EXISTS game (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS game_state (
    game_id     BIGINT      NOT NULL PRIMARY KEY,
    status      VARCHAR(50) NOT NULL,
    is_finished BOOLEAN     NOT NULL DEFAULT FALSE,
    FOREIGN KEY (game_id) REFERENCES game (id)
);

CREATE TABLE IF NOT EXISTS piece (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT      NOT NULL,
    pos_row     INT         NOT NULL,
    pos_col     INT         NOT NULL,
    piece_name    VARCHAR(10) NOT NULL,
    team    VARCHAR(10) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (id)
);