DROP TABLE IF EXISTS piece;
DROP TABLE IF EXISTS turn;

CREATE TABLE piece (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       dtype VARCHAR(30) NOT NULL,
                       team VARCHAR(10) NOT NULL,
                       column_index INT NOT NULL,
                       row_index INT NOT NULL
);

CREATE TABLE turn (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      team VARCHAR(10) NOT NULL
);
