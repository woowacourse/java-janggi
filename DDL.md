DROP DATABASE IF EXISTS janggi;
CREATE DATABASE janggi;

USE janggi;

CREATE TABLE piece (
    piece_id INT AUTO_INCREMENT PRIMARY KEY,
    x_coordinate INT NOT NULL,
    y_coordinate INT NOT NULL,
    piece_type VARCHAR(6) NOT NULL,
    team CHAR(3) NOT NULL
);

CREATE TABLE turn (
    team CHAR(3) PRIMARY KEY DEFAULT 'CHO',
    round INT NOT NULL DEFAULT 1
);

INSERT INTO turn (team, round) VALUES ('CHO', 1);
