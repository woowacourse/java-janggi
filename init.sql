USE chess;

-- 게임 상태 테이블
CREATE TABLE IF NOT EXISTS `janggi_game` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_dynasty VARCHAR(10) NOT NULL,
    was_last_passed BOOLEAN NOT NULL DEFAULT FALSE,
    is_finished BOOLEAN NOT NULL DEFAULT FALSE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- 기물 상태 테이블
CREATE TABLE IF NOT EXISTS `janggi_piece` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    janggi_game_id BIGINT NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    dynasty VARCHAR(10) NOT NULL,
    type VARCHAR(20) NOT NULL,
    FOREIGN KEY (janggi_game_id) REFERENCES `janggi_game`(id) ON DELETE CASCADE
    );
