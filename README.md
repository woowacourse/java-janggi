# java-janggi
장기 미션 저장소

# 의문점
- [ ] King, Cannon의 타입을 알기위해 getClass()를 사용하지 않는 방법을 연구해라
  - [ ] instanceOf는 왜 나쁜가?
  - [ ] 필드에 Type을 생성한다면, instanceOf랑 뭐가 다른가 GetType()

- 상태를 가지지 않으면 좋은 객체가 아니다?
  - 그러면 왜 데이터 중심설계가 아닌 행위중심 설계를 하지?
  - 상태는 행위의 부속아닌가?

# 코드 의도
- isInstanceOf 혹은 부모클래스 내 자식타입이 정의된 Enum을 사용하지 않고 해결하고싶어 Piece를 상속받지 않고,
  타입과 전략패턴의 조합으로 구현하였습니다.

📌 미션을 수행하며 반드시 고민할 것
✅ 장기의 말(駒)들은 어떻게 객체로 나누어야 하는가?
✅ 각 말의 이동 규칙을 어떻게 설계할 것인가?
✅ 객체 간의 책임을 어떻게 나눌 것인가?
✅ 어떤 원칙을 적용해야 더 좋은 설계를 할 수 있을까?

## 미션 목표
- [ ] 확장성을 고려한 코드 작성하기
  - [ ] 코드가 확장되어도 변경되는 코드가 최소화되도록 노력하기
- [ ] 객체에 책임과 역할 부여잘하기
- [ ] 프로그래밍 요구사항 전부 지켜보기
- [ ] 객체지향 프로그래밍이 무엇인지 고민하기
- [ ] 우리가 작성한 코드에 모두 이유를 갖기

## 기능 요구사항 (보드 초기화, 기물 이동)
![img.png](img.png)

### 1. 보드판을 초기화한다
#### 입력 객체
- [ ] 馬象馬象(마상마상), 象馬象馬(상마상마), 馬象象馬(마상상마), 象馬馬象(상마마상) 선택을 두 번 받는다.

#### 보드판 객체
- [X] 초기화된 보드판을 생성한다
  - [X] 보드판은 세로 10, 가로 9의 좌표로 이루어져있다
  - [X] 각 기물은 선택지를 고려하여 알맞게 위치한다

#### 초나라 객체
- [X] 궁 1개, 차 2개, 포 2개, 마 2개, 상 2개, 사 2개, 졸 5개를 갖는다.
- [X] 초나라 객체는 각 기물을 위치시킨다.
  - [X] 궁 (5,9)
  - [X] 사 (4,10), (6,10)
  - [X] 차 (1,10), (9,10)
  - [X] 포 (2,8), (8,8)
  - [X] 마, 상은 입력받은 위치로 생성한다
  - [X] 졸 (1,7), (3,7), (5,7), (7,7), (9,7)

#### 한나라 객체
- [X] 궁 1개, 차 2개, 포 2개, 마 2개, 상 2개, 사 2개, 병 5개를 갖는다.
- [x] 한나라 객체는 각 기물을 위치시킨다.
  - [X] 궁 (5,2)
  - [X] 사 (4,1), (6,1)
  - [X] 차 (1,1), (9,1)
  - [X] 포 (2,3), (8,3)
  - [x] 마, 상은 입력받은 위치로 생성한다
  - [X] 졸 (1,4), (3,4), (5,4), (7,4), (9,4)

### 2. 기물을 구현한다
#### 기물 객체(궁(宮), 차(車), 포(包), 마(馬), 상(象), 사(士), 졸,병(兵))
- 기물은 불변 객체로 만든다
- 위치도 불변 객체로 만든다

- [ ] 포(包)의 이동 규칙
  - [ ] 포(包)를 뛰어넘지 못한다
  - [ ] 포(包)를 잡을 수 없다

- [ ] 각 기물은 개별 이동 규칙을 갖는다
  - [x] 차(車)는 상하좌우 한 방향으로 움직인다
  - [ ] 포(包)는 상하좌우 직선으로 한 개의 기물을 건너뛰어야 움직일 수 있다
    - [ ] 포(包)는 두 개의 기물을 건너뛸 수 없다
    - [ ] 상하좌우 직선으로 움직인다
  - [ ] 마(馬)는 상하좌우 한 칸 직선으로 움직인 뒤, 움직인 방향 좌우 대각선으로 한 칸 움직일 수 있다
    - [ ] 상하좌우 직선으로 움직인다
  - [ ] 상(象)는 상하좌우 한 칸 직선으로 움직인 뒤, 움직인 방향 좌우 대각선으로 두 칸 움직일 수 있다
  - [ ] 병(兵)은 상좌우 한 칸 직선으로 움직일 수 있다

#### 보드판
- [ ] 각 기물은 공통 규칙을 갖는다
  - [ ] 이동하려한 최종 위치에 자신팀 기물이 있을 경우 그곳으로 이동할 수 없다.
  - [ ] 이동하려한 최종 위치에 상대팀 기물이 있을 경우 상대팀 기물을 제거하고 이동한다.
  - [ ] 최종 위치까지의 이동 경로에 자신,상대 팀 기물이 있을 경우 이동할 수 없다

### 3. 기물을 이동한다
#### 입력 객체
- [ ] 움직일 기물 정보를 입력받는다

`움직이실 기물과 기물의 좌표를 적어주세요 ex)차(1,2)`

- [ ] 이동할 목적지를 입력받는다


#### 출력 객체
- [ ] 선택할 기물의 이동 가능한 좌표들을 출력한다

`움직이실 차의 좌표를 골라주세요 (움직일 수 있는 좌표들을 출력한다)`

#### 기물 객체
- [ ] 입력받은 좌표로 이동한다


```angular2html
한나라는 아래 중 하나를 선택해주세요.
1. 馬象馬象(마상마상)
2. 象馬象馬(상마상마)
3. 馬象象馬(마상상마)
4. 象馬馬象(상마마상)

초나라는 아래 중 하나를 선택해주세요
1. 馬象馬象(마상마상)
2. 象馬象馬(상마상마)
3. 馬象象馬(마상상마)
4. 象馬馬象(상마마상)

   1 2 3 4 5 6 7 8 9
ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
1 |차 마 상 사 ㅡ 사 상 마 차 
2 |ㅡ ㅡ ㅡ ㅡ 궁 ㅡ ㅡ ㅡ ㅡ
3 |ㅡ 포 ㅡ ㅡ ㅡ ㅡ ㅡ 포 ㅡ
4 |병 ㅡ 병 ㅡ 병 ㅡ 병 ㅡ 병
5 |ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ
6 |ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ
7 |졸 ㅡ 졸 ㅡ 졸 ㅡ 졸 ㅡ 졸
8 |ㅡ 포 ㅡ ㅡ ㅡ ㅡ ㅡ 포 ㅡ
9 |ㅡ ㅡ ㅡ ㅡ 궁 ㅡ ㅡ ㅡ ㅡ
10|차 마 상 사 ㅡ 사 상 마 차

한나라 움직이실 기물과 기물의 좌표를 입력해주세요.(ex.기물(x,y))
차(1,1)

이동할 위치를 선택해주세요.
(1,2)

1 2 3 4 5 6 7 8 9
ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
1 |ㅡ 마 상 사 ㅡ 사 상 마 차
2 |차 ㅡ ㅡ ㅡ 궁 ㅡ ㅡ ㅡ ㅡ
3 |ㅡ 포 ㅡ ㅡ ㅡ ㅡ ㅡ 포 ㅡ
4 |병 ㅡ 병 ㅡ 병 ㅡ 병 ㅡ 병
5 |ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ
6 |ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ
7 |졸 ㅡ 졸 ㅡ 졸 ㅡ 졸 ㅡ 졸
8 |ㅡ 포 ㅡ ㅡ ㅡ ㅡ ㅡ 포 ㅡ
9 |ㅡ ㅡ ㅡ ㅡ 궁 ㅡ ㅡ ㅡ ㅡ
10|차 마 상 사 ㅡ 사 상 마 차
```
