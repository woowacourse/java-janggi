CREATE DATABASE IF NOT EXISTS chess;
USE chess;

CREATE TABLE IF NOT EXISTS janggi (
                                    id VARCHAR(36) PRIMARY KEY,
    turn VARCHAR(10) COMMENT '현재턴'
    );

CREATE TABLE IF NOT EXISTS piece (

    id VARCHAR(36) PRIMARY KEY,
    team VARCHAR(20) COMMENT '팀',
    y_axis VARCHAR(10) COMMENT '행 위치',
    x_axis VARCHAR(10) COMMENT '열 위치',
    piece_type VARCHAR(20) COMMENT '기물타입',
    janggi_id VARCHAR(36) COMMENT '장기id',
    FOREIGN KEY (janggi_id) REFERENCES janggi(id)
    );

CREATE INDEX IF NOT EXISTS idx_piece_janggi_id ON piece(janngi_id);
