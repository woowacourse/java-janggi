CREATE TABLE IF NOT EXISTS BOARDS
(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `turn` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS PIECES
(
    `board_id` BIGINT       NOT NULL,
    `type`     VARCHAR(255) NOT NULL,
    `camp`     VARCHAR(255) NOT NULL,
    `column`   INT          NOT NULL,
    `row`      INT          NOT NULL,

    CONSTRAINT pk_board_piece PRIMARY KEY (`board_id`, `column`, `row`),
    CONSTRAINT fk_board FOREIGN KEY (`board_id`) REFERENCES BOARDS (`id`)
);
