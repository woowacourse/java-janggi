# DB 설정 방법

#### 데이터베이스는 Test db, Prod db로 나뉘어져 있습니다.

### Prod : janggi
### Test : janggi_test

### 각각의 DB를 생성 후, 실행해야 합니다.

### docker-compose.yml 
```
version: "3.9"
services:
  db:
    image: mysql:8.0.28
    platform: linux/x86_64
    restart: always
    ports:
      - "13306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: chess
      MYSQL_USER: user
      MYSQL_PASSWORD: password
      TZ: Asia/Seoul
    volumes:
      - ./db/mysql/data:/var/lib/mysql
      - ./db/mysql/config:/etc/mysql/conf.d
      - ./db/mysql/init:/docker-entrypoint-initdb.d
```

### docker-compose -p chess up -d

## DB DDL Query문
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
