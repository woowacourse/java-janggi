### Docker 정보

Docker version 28.0.1, build 068a01e

### MySQL 서버 버전 정보

MySQL 서버의 버전 정보: 8.0.28

### Docker 실행하기

docker-compose -p janggi up -d

### Docker 정지하기

docker-compose -p janggi down

### 테이블 생성

```보드 SQL
CREATE TABLE Board (
    row_value INT NOT NULL,
    column_value INT NOT NULL,
    type VARCHAR(64) NOT NULL,
    dynasty VARCHAR(64) NOT NULL,
    PRIMARY KEY (row_value, column_value)
);
```

```턴 SQL
CREATE TABLE Turn (
    id TINYINT NOT NULL PRIMARY KEY DEFAULT 1,
    turn INT NOT NULL
);
INSERT INTO Turn (id, turn) VALUES (1, 0);
```
