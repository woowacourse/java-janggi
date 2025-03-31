CREATE TABLE IF NOT EXISTS `room`
(
    `room_id` varchar(255) NOT NULL,
    `status`  varchar(255) NOT NULL,
    `turn`    varchar(255) NOT NULL,
    PRIMARY KEY (room_id)
);

CREATE TABLE IF NOT EXISTS `piece`
(
    `piece_id`   bigint       NOT NULL AUTO_INCREMENT,
    `position_x` bigint       NOT NULL,
    `position_y` bigint       NOT NULL,
    `piece_type` varchar(255) NOT NULL,
    `team`       varchar(255) NOT NULL,
    `room_id`    varchar(255) NOT NULL,
    PRIMARY KEY (piece_id),
    FOREIGN KEY (room_id) REFERENCES room (room_id)
);
