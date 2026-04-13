-- 데이터베이스 생성 및 선택
CREATE DATABASE IF NOT EXISTS janggi_db;
USE janggi_db;

-- 게임 정보 테이블
CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cho_player VARCHAR(50) NOT NULL,
    han_player VARCHAR(50) NOT NULL,
    current_turn VARCHAR(10) NOT NULL,
    is_finished BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 보드 상태 정보 테이블
CREATE TABLE IF NOT EXISTS board_state (
    game_id BIGINT,
    row_index INT,
    col_index INT,
    piece_type VARCHAR(20),
    side VARCHAR(10),
    piece_number VARCHAR(10),
    PRIMARY KEY (game_id, row_index, col_index),
    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
);