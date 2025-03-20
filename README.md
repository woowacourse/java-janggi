# java-janggi

장기 미션 저장소

과제 진행 요구 사항
첫 시작시의 장기의 보드판 그리고 각 기물을 구현하라

장기 기획서 확인
1단계에서는 장기 기물들의 동작은 구현하지 않는다.
구현 전에 해야 할 일

개발을 시작하기 전에 어떤 기능을 구현해야 하는지 정리하라.
정리한 내용을 .md 파일로 제출하라.
궁(宮) 또는 궁성(宮城)의 영역은 구현하지 마라.

즉, 장기판의 왕(將, 帥)과 사(士, 仕)의 이동과 관련된 규칙은 고려하지 않는다.
📌 즉, 개발 과정에서 단순히 코드만 작성하지 말고,
나는 왜 이렇게 구현할까? 라는 고민을 정리하는 것이 중요하다.

## 기능 구현

보드는 초기화하지 않습니다

### 기물들, Military

- [x] 기물을 초기화한다.
- [ ] 상차림 선택지 대로 마와 상을 배치한다.
- [ ] 어떤 좌표에 기물이 존재하는지 확인한다.

### 기물, unit.Unit

- [ ] 기물이 갈 수 있는 경로를 모두 계산한다.
    - [ ] 궁 : 궁은 아무 기능이 없다.
    - [ ] 사 : 사는 아무 기능이 없다.
    - [ ] 졸 : 직진(좌, 우, 상) 1번.
    - [ ] 마 : 작진(좌, 우, 상, 하) 1번, 대각선 이동 1번.
    - [ ] 차 : 직진(좌, 우, 상, 하)만 가능하다
    - [ ] 포 : 무조건 기물을 한칸 점프해야 하며, 직진만 가능하다.
    - [ ] 상 : 직진(좌, 우, 상, 하) 1번, 대각선 이동 2번.

### 팀, unit.Team

- [ ] 어느 방향이 앞인지 결정한다.

### 좌표, domain.Position

- [x] 좌표가 보드를 넘는지 안 넘는지 판단한다.

### 출력 형식

```
* | A B C D E F G H I 
----------------------
0 | C E H S . S E H C
1 | . . . . K . . . . 
2 | . . . . . . . . . 
3 | . B . . . . . B .
4 | A . A . A . A . A
5 | . . . . . . . . .
6 | A . A . A . A . A
7 | . B . . . . . B .
8 | . . . . K . . . .
9 | C H E S . S E H C

기물 별 대표 문자
궁 : K
졸, 병 : A
사 : S
마 : H
차 : C
포 : B
상 : E
```
