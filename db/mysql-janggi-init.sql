create user 'username'@'localhost' identified by 'password';
grant all privileges on *.* to 'username'@'localhost';
flush privileges;
CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


USE janggi;

CREATE TABLE IF NOT EXISTS piece (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  x INT NOT NULL,
  y INT NOT NULL,
  piece_type VARCHAR(20) NOT NULL,
  color VARCHAR(20) NOT NULL
);
