

```
CREATE TABLE `piece` (
`piece_id` int NOT NULL AUTO_INCREMENT,
`column` varchar(10) NOT NULL,
`row` varchar(10) NOT NULL,
`team` varchar(10) NOT NULL,
`type` varchar(10) NOT NULL,
PRIMARY KEY (`piece_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3

```

```
CREATE TABLE `game` (
  `game_id` int NOT NULL DEFAULT '1',
  `turn` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

```
