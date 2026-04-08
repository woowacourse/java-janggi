CREATE TABLE BOARDS
(
    id   BIGINT  NOT NULL,
    turn VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PIECES
(
    board_id BIGINT       NOT NULL,
    type     VARCHAR(255) NOT NULL,
    camp     VARCHAR(255) NOT NULL,
    column   INT          NOT NULL,
    row      INT          NOT NULL,

    CONSTRAINT pk_board_piece PRIMARY KEY (board_id, column, row),
    CONSTRAINT fk_board FOREIGN KEY (board_id) REFERENCES BOARDS (id)
);
