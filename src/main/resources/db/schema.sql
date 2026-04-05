DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS game;

CREATE TABLE game (
                      game_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      cho_name VARCHAR(50) NOT NULL COMMENT '선공 플레이어명',
                      han_name VARCHAR(50) NOT NULL COMMENT '후공 플레이어명',
                      current_turn VARCHAR(10) NOT NULL COMMENT '현재 차례 (CHO, HAN)',
                      status VARCHAR(20) NOT NULL COMMENT '상태 (PROGRESS, CHO_WIN, HAN_WIN)',
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '게임 생성 일시'
);

CREATE TABLE board (
                       game_id BIGINT NOT NULL,
                       row_idx INT NOT NULL COMMENT '행 좌표 (0~9)',
                       col_idx INT NOT NULL COMMENT '열 좌표 (0~8)',
                       team VARCHAR(10) NOT NULL COMMENT '기물 소속 팀 (CHO, HAN)',
                       piece_type VARCHAR(10) NOT NULL COMMENT '기물 종류 (JANG, CHA, PO, MA, SANG, SA, JOL)',
                       PRIMARY KEY (game_id, row_idx, col_idx),
                       CONSTRAINT fk_board_game FOREIGN KEY (game_id)
                           REFERENCES game (game_id) ON DELETE CASCADE
);
