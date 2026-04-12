-- 1. 게임 테이블 (메타데이터)
CREATE TABLE game (
                      id VARCHAR(36) PRIMARY KEY,
                      name VARCHAR(50) NOT NULL UNIQUE,
                      status VARCHAR(20) NOT NULL,       -- PLAYING, CHO_WIN, HAN_WIN, DRAW
                      current_turn VARCHAR(10) NOT NULL  -- CHO, HAN
);

-- 2. 기물 상태 테이블 (1번 테이블을 참조)
CREATE TABLE piece (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       game_id VARCHAR(36) NOT NULL,           -- 어떤 게임판에 속해있는지 (FK)
                       piece_name VARCHAR(20) NOT NULL,   -- CHO_SOLDIER, HAN_CHARIOT 등
                       camp VARCHAR(3) NOT NULL,
                       row_index INT NOT NULL,            -- Y 좌표 (0~9)
                       column_index INT NOT NULL,         -- X 좌표 (0~8)


                       FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
);