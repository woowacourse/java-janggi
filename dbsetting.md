# 자동 설정 방법

1. docker desktop 실행
2. 애플리케이션 실행
   - **docker 컨테이너 생성, DB 초기화 등의 작업은 자동으로 진행됩니다.**
   - **프로그램을 강제 종료할 시 docker 컨테이너가 종료되지 않을 수 있습니다.**

# 수동 설정 방법

1. docker desktop 실행
1. [run-docker.sh](./docker/run-docker.sh) 실행
2. 자바 애플리케이션 실행
3. [stop-docker.sh](./docker/stop-docker.sh) 실행

<hr>

### [docker-compose.yml](./docker/docker-compose.yml)

```yaml
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
         MYSQL_DATABASE: janggi
         MYSQL_USER: user
         MYSQL_PASSWORD: password
         TZ: Asia/Seoul
      volumes:
         - ./db/mysql/data:/var/lib/mysql
         - ./db/mysql/config:/etc/mysql/conf.d
         - ./init.sql:/docker-entrypoint-initdb.d/init.sql

```

### [DDL 스크립트](./docker/init.sql)
```sql
CREATE DATABASE janggi;

USE janggi;

CREATE TABLE piece (
    piece_id INT AUTO_INCREMENT PRIMARY KEY,
    x_coordinate INT NOT NULL,
    y_coordinate INT NOT NULL,
    piece_type VARCHAR(6) NOT NULL,
    team CHAR(3) NOT NULL
);

CREATE TABLE turn (
    team CHAR(3) PRIMARY KEY DEFAULT 'CHO',
    round INT NOT NULL DEFAULT 1
);

INSERT INTO turn (team, round) VALUES ('CHO', 1);
```
