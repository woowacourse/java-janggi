CREATE TABLE piece (
    side VARCHAR(50) NOT NULL,
    piece_type VARCHAR(50) NOT NULL,
    position_x TINYINT UNSIGNED NOT NULL,
    position_y TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (position_x, position_y)
);
