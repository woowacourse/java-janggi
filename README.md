# java-janggi

장기 미션 저장소

## 기물

- [x] 기물은 위치를 갖는다.
    - [x] X축 (0-8), Y축(0-9)로 구현한다.
- [x] 기물은 팀을 갖는다.
    - [x] 팀은 한나라, 초나라로 나뉘어 진다.
- [x] 기물은 움직인다.
    - [x] 다른 기물과 상관없이 갈 수 있는 경로를 얻는다.
- [x] 졸
    - [x] 졸 기물은 좌, 우로 움직일 수 있고 한나라일 경우 하, 초나라일 경우 상으로 움직일 수 있다.
    - [x] 졸 기물은 궁성 안이며 기둥의 위치일 경우 대각선으로 움직일 수 있다.
- [x] 마
    - [x] 마 기물은 직진 -> 직진 방향의 대각선 한 칸으로 움직인다. (ex 오른쪽 -> 오른쪽 위)
- [x] 사
    - [x] 사 기물은 상, 하, 좌, 우, 대각선으로 움직일 수 있다.
        - [x] 기둥의 위치일 경우만 대각선으로 움직일 수 있다.
    - [x] 사 기물은 궁성 안에서만 움직일 수 있다.
- [x] 왕
    - [x] 왕 기물은 상, 하, 좌, 우, 대각선으로 움직일 수 있다.
        - [x] 기둥의 위치일 경우만 대각선으로 움직일 수 있다.
    - [x] 왕 기물은 궁성 안에서만 움직일 수 있다.
- [x] 상
    - [x] 상 기물은 직진 -> 직진 방향의 대각선 두 칸으로 움직인다. (ex 오른쪽 -> 오른쪽 위 -> 오른쪽 위)
- [x] 차
    - [x] 차 기물은 상, 하, 좌, 우 모두 원하는 만큼 움직일 수 있다.
    - [x] 차 기물은 궁성 안이며 기둥의 위치일 경우 대각선으로 움직일 수 있다. 하지만 대각선으로 움직일 경우 궁성 밖으로 나가지 못한다.
- [x] 포
    - [x] 포 기물은 상, 하, 좌, 우 모두 원하는 만큼 움직일 수 있다.
    - [x] 포 기물은 다른 포 기물을 제외한 다른 기물을 넘어 뛰어야한다.
    - [x] 포 기물은 궁성 안이며 기둥의 위치일 경우 대각선으로 움직일 수 있다. 하지만 대각선으로 움직일 경우 궁성 밖으로 나가지 못한다.

## 위치

- [x] 위치는 x축 기준 0-8, y축 기준 0-9만 가능하다.

## 기물 모음

- [x] 다른 기물들의 위치를 받아 각 특징에 따라 최종적으로 갈 수 있는 경로를 선별한다.
- [x] 모든 기물은 다른 기물을 통과하여 갈 수 없다.
- [x] 기물은 다른 기물을 잡을 수 있다.
    - [x] 기물은 같은 팀을 잡지 못하고, 오직 다른 팀만 잡을 수 있다.
    - [x] 포 기물은 포 기물을 잡지 못한다.

## 경로

- [x] 위치를 받아, 경로에 포함되는지 확인한다.
- [x] 위치를 받아, 경로의 최종 도착지인지 확인한다.

## 보드

- [x] 보드를 초기화 한다.
    - [x] 기물을 초기 위치에 둔다.
        - [x] 마상마상, 마상상마, 상마상마, 상마마상을 고른다. 해당 입력에 따라 장기판을 구성한다.
- [x] 게임을 진행한다.

## 턴

- [x] 한나라, 초나라 순으로 게임을 진행한다.
- [x] 턴을 바꿀 수 있다.

## 게임 결과

- [x] General, 왕 기물이 잡히면 게임은 종료 된다.
    - [x] 왕 기물을 잡은 팀은 점수와 상관없이 승리한다.
- [x] 왕 기물끼리 남아있을 때 게임은 종료 된다.
    - [x] 각 팀의 점수를 출력한다.
- [x] 서로가 게임 종료에 동의하면 남아있는 기물의 점수를 계산 후 출력한다.
    - [x] 해당 턴이 아닌 사람은 1.5의 가산 점수를 받는다.

## 데이터베이스 사용법

- [x] 도커 컴포즈를 실행시켜 컨테이너를 구성한다.
- [x] 아래 테이블을 추가한다.
- [x] 테스트 데이터베이스, 실제 데이터베이스 모두 생성한다.

```
CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE janggi_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

CREATE TABLE Piece (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       piece_type VARCHAR(50) NOT NULL,
                       team VARCHAR(10) NOT NULL,
                       x INT NOT NULL,
                       y INT NOT NULL
);

CREATE TABLE Turn (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      team VARCHAR(10)
);

USE janggi_test;

CREATE TABLE Piece (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       piece_type VARCHAR(50) NOT NULL,
                       team VARCHAR(10) NOT NULL,
                       x INT NOT NULL,
                       y INT NOT NULL
);

CREATE TABLE Turn (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      team VARCHAR(10)
);


```

- [x] 이후 인텔리제이와의 연결 후 사용한다.
