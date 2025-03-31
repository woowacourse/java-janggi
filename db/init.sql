USE janggi;

CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,   -- 게임의 고유 ID
    current_turn VARCHAR(10) NOT NULL,   -- 현재 턴(초나라, 한나라)
    status VARCHAR(20) NOT NULL -- 게임 진행 상태
);

CREATE TABLE IF NOT EXISTS piece (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 각 장기 말의 고유 ID
    piece_type VARCHAR(20) NOT NULL,          -- 장기 말의 이름 (예: 차, 마, 포, 병 등)
    current_turn VARCHAR(10) NOT NULL,   -- 현재 턴(초나라, 한나라)
    row_index INT NOT NULL,             -- 행 위치 (0~9 범위)
    column_index INT NOT NULL,          -- 열 위치 (0~8 범위)
    game_id BIGINT NOT NULL,               -- 게임과 연관된 ID
    FOREIGN KEY (game_id) REFERENCES game(id)
);

