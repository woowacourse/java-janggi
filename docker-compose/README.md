# Docker MySQL 실행하기

docekr-compose.yml 파일이 있는 경로에서, docker 명령어로 Server를 실행

```shell
# Docker 실행하기
docker-compose -p chess up -d

# Docker 정지하기
docker-compose -p chess down
```

# mysql woekbench 접속하기

mysql workbench를 실행하고, 다음과 같이 설정하여 접속한다.
![img](https://techcourse-storage.s3.ap-northeast-2.amazonaws.com/e802604856224a0999a41966dade1b87)

기본 아이디와 비밀번호는 다음과 같다.

```text
root
```

# 도커 실행 후

도커로 mysql를 최소 실행하면 db/mysql-janggi-init.sql 파일을 실행하여 테이블을 생성한다.
