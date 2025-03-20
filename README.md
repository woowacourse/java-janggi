# java-janggi

## 기능 목록

1. 게임 시작
    - [x] 기물 생성
        1. 초나라/한나라로 팀을 가짐(초나라: 초록색, 한나라: 빨간색)
    - [x] 기물 세팅

    3. [X] 세팅된 기물 출력
2. [X] 초나라 먼저 이동하고자 하는 기물 위치 입력
    1. [X] 입력 예시) 1,1
    2. 입력값 검증
        - [ ] split “,”
        - [ ] 구분자 없으면 예외 발생
        - [X] 범위 확인 0-8 / 0-9
        - [X] 해당 위치에 말이 있는지
        - [X] 초나라 말인지 확인
3. [X] 해당 기물을 이동 시킬 위치 입력 받기
    - 입력 예시) 2,2
    - 입력값 검증
        - [ ] 1,2 같음
        - [X] 보드 범위에 있는지
        - [x] 룰 (해당 말이 이동하는 룰이 맞는지)
        - [x] 경로상에 다른 말이 없는지
        - [x] 종점에 어떤 팀의 기물이 있는지 (적팀이면 가능 / 우리팀이면 불가능)
4. [x] 해당 기물 이동
    - [x] 만약 해당 위치에 적팀 기물이 있다면 제거후 자리 차지
5. 잡았으면 잡았다는 메시지 출력
6. [X] 보드판 출력
7. [X] 한나라 반복
8. 게임 종료 조건
    1. 왕이 죽으면 패배
