USE janggi;

CREATE TABLE piece
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    column_name VARCHAR(1)  NOT NULL,
    row_name    VARCHAR(1)  NOT NULL,
    country     VARCHAR(3)  NOT NULL,
    piece_type  VARCHAR(10) NOT NULL
);

CREATE TABLE turn
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    country_name VARCHAR(3) NOT NULL
);
