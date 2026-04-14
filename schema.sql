CREATE TABLE game (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      game_status VARCHAR(20),
                      current_camp VARCHAR(10),
                      han_score DOUBLE,
                      cho_score DOUBLE
);

CREATE TABLE board (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       game_id BIGINT,
                       col_pos INT,
                       row_pos INT,
                       piece_type VARCHAR(20),
                       camp VARCHAR(10)
);
