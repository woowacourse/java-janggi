USE janggi;

CREATE TABLE IF NOT EXISTS board (
    position_row INT NOT NULL,
    position_column INT NOT NULL,
    piece_type VARCHAR(50) NOT NULL,
    piece_color VARCHAR(10) NOT NULL,
    PRIMARY KEY (position_row, position_column)
);
