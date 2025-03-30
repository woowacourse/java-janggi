CREATE TABLE piece
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    type  VARCHAR(20) NOT NULL,
    camp  VARCHAR(10) NOT NULL,
    pos_x INT         NOT NULL,
    pos_y INT         NOT NULL
);
