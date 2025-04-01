CREATE DATABASE IF NOT EXISTS chess;
USE chess;

CREATE TABLE IF NOT EXISTS janggi
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    turn VARCHAR(10) COMMENT '현재턴'
);

CREATE TABLE IF NOT EXISTS piece
(

    id         INT AUTO_INCREMENT PRIMARY KEY,
    team       VARCHAR(20) COMMENT '팀',
    y_axis     VARCHAR(10) COMMENT '행 위치',
    x_axis     VARCHAR(10) COMMENT '열 위치',
    piece_type VARCHAR(20) COMMENT '기물타입'
);
