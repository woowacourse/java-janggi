CREATE USER 'root'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE janggi;

CREATE TABLE piece (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       type ENUM('CHA', 'MA', 'SANG', 'SA', 'JANGGUN', 'PO', 'BYEONG', 'JOL') NOT NULL,
                       row_position INT NOT NULL,
                       col_position INT NOT NULL,
                       is_alive BOOLEAN DEFAULT TRUE,
                       score INT DEFAULT 0,
                       country ENUM('HAN', 'CHO') NOT NULL
);

CREATE TABLE game_state (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            current_turn ENUM('HAN', 'CHO') NOT NULL,
                            status ENUM('ONGOING', 'FINISHED') DEFAULT 'ONGOING'
);
