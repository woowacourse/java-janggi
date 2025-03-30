# 데이터베이스 세팅

1. docker directory 생성 후 그 하위에 [docker-compose.yml](#docker-composeyml) 파일을 생성
2. docker-compose.yml 파일이 있는 경로에서 터미널로 명령어 실행
   ```
   실행
   docker-compose -p janggi up -d
   
   정지
   docker-compose -p janggi down
   ```
3. 아래 정보를 바탕으로 DB에 연결
   ```text
   Hostname : localhost
   Port : 13306
   Username : root
   Password : root 
   ```
4. [DDL 스크립트](#ddl-스크립트) 실행
5. 자바 애플리케이션 실행

## docker-compose.yml

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
      - ./db/mysql/init:/docker-entrypoint-initdb.d
```

## DDL 스크립트
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
