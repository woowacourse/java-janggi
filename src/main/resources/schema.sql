CREATE DATABASE IF NOT EXISTS janggi;
USE janggi;

-- 초기 배치
CREATE TABLE IF NOT EXISTS formation_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    template_name VARCHAR(50) NOT NULL,
    team VARCHAR(10) NOT NULL,

    CONSTRAINT uq_template_name_team UNIQUE (template_name, team)
);

-- 게임 테이블 초기화
CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    current_turn VARCHAR(10) NOT NULL,
    cho_formation_id BIGINT NOT NULL,
    han_formation_id BIGINT NOT NULL,

    CONSTRAINT fk_game_cho_formation
    FOREIGN KEY (cho_formation_id)
    REFERENCES formation_template(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT fk_game_han_formation
    FOREIGN KEY (han_formation_id)
    REFERENCES formation_template(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 상차림 실제 기물 배치
CREATE TABLE IF NOT EXISTS formation_piece_layout (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    formation_template_id BIGINT NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    board_row INT NOT NULL,
    board_column INT NOT NULL,

    CONSTRAINT fk_layout_template
    FOREIGN KEY (formation_template_id)
    REFERENCES formation_template(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT uq_layout_position
    UNIQUE (formation_template_id, board_row, board_column)
);

-- 조회용 테이블(read-model): 실제 보드 저장 상태
CREATE TABLE IF NOT EXISTS current_piece_position (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    piece_team VARCHAR(10) NOT NULL,
    board_row INT NOT NULL,
    board_column INT NOT NULL,

    CONSTRAINT fk_current_board_game
    FOREIGN KEY (game_id)
    REFERENCES game(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT uq_board_position
    UNIQUE (game_id, board_row, board_column)
);


-- 이벤트 소싱 테이블(쓰기 전용): 각 이동 상태 저장
CREATE TABLE IF NOT EXISTS move_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    version BIGINT NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    piece_team VARCHAR(10) NOT NULL,
    from_row INT NOT NULL,
    from_column INT NOT NULL,
    to_row INT NOT NULL,
    to_column INT NOT NULL,

    CONSTRAINT fk_move_event_game
    FOREIGN KEY (game_id)
    REFERENCES game(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT uq_move_sequence
    UNIQUE (game_id, version)
);

-- 상차림 템플릿 데이터 삽입
INSERT INTO formation_template (template_name, team) VALUES ('INNER_ELEPHANT', 'CHO');
INSERT INTO formation_template (template_name, team) VALUES ('OUTER_ELEPHANT', 'CHO');
INSERT INTO formation_template (template_name, team) VALUES ('RIGHT_ELEPHANT', 'CHO');
INSERT INTO formation_template (template_name, team) VALUES ('LEFT_ELEPHANT', 'CHO');

INSERT INTO formation_template (template_name, team) VALUES ('INNER_ELEPHANT', 'HAN');
INSERT INTO formation_template (template_name, team) VALUES ('OUTER_ELEPHANT', 'HAN');
INSERT INTO formation_template (template_name, team) VALUES ('RIGHT_ELEPHANT', 'HAN');
INSERT INTO formation_template (template_name, team) VALUES ('LEFT_ELEPHANT', 'HAN');
