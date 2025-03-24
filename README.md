# java-janggi

장기 미션 저장소

## 기능 구현

### 장기(장기판), Janggi

- [x] 생성 시 장기판을 초기화한다.
- [x] 장기판의 위치를 선택하면, 해당 장기말이 움직일 수 있는 모든 경로를 반환한다.
- [x] 장기말을 장기판의 위치를 이용해 선택하고, 도착지를 지정해 해당 장기말을 움직일 수 있다.
    - [x] 도착지가 규칙에 벗어나면 예외를 발생시킨다.
    - [x] 도착지에 적군이 있다면 잡는다.

### 장기말(기물), Unit

- [x] 해당 기물이 위치로부터 갈 수 있는 모든 위치를 반환한다.
    - [x] 반환하는 경로는 다른 기물의 위치를 고려하지 않는다.
    - [x] 장기판 내의 좌표인지는 고려한다.

### 장기말 이동 규칙, UnitRule

- [x] 각 장기말의 이동 규칙을 알고있다.

### 장기판 내의 위치, Position

- [x] 장기판 내의 위치를 관리한다.
- [x] 범위를 벗어나서 위치를 생성하려하면 예외를 발생시킨다.

### 출력 형식

```
* | 00 01 02 03 04 05 06 07 08 
--------------------------------
0 | CH EL HR GD .. GD EL HR CH
1 | .. .. .. .. GN .. .. .. .. 
2 | .. CN .. .. .. .. .. CN .. 
3 | SD .. SD .. SD .. SD .. SD
4 | .. .. .. .. .. .. .. .. ..
5 | .. .. .. .. .. .. .. .. ..
6 | SD .. SD .. SD .. SD .. SD
7 | .. CN .. .. .. .. .. CN ..
8 | .. .. .. .. GN .. .. .. ..
9 | CH EL HR GD .. GD EL HR CH

기물 별 대표 문자
궁(General) : GN
졸, 병(Soldiers) : SD
사(Guards) : GD
마(Horses) : HR
차(Chariots) : CH
포(Cannons) : CN
상(Elephants) : EL
```

## 1단계 블랙잭 피드백

- [x] 장기 네이밍 변경하기 [장기 기획서](https://en.wikipedia.org/wiki/Janggi)
- [x] `Team#isFront()` 메서드 삭제
- [x] `JanggiTest` 불필요한 줄바꿈 제거
- [x] `Position#isHorizontal()` -> `isHorizontalOrVertical()` 메서드 네임 변경
- [x] `ChariotUnitRule#calculateAllRoute()` 에서 `@Override` 애너테이션 붙이기
- [x] `CannonUnitRule#calculateEndPoint()` 접근제한자 `private`으로 변경
- [x] `OutputView`에서 if문에 도달하지 않는 경우 예외 발생시키기
- [x] `Janggi` 외부에서 주입하기
    - [ ] 주입하는 위치 고민
- [x] `Janggi#changeTeam()`구현 로직 `Team` 객체에 메시지를 보내는 방향으로
- [x] `Unit` -> 동등성 정의 로직 삭제
- [x] `Unit` 객체 인스턴스 변수 3개를 2개로 줄이기
- [x] 에외 메시지 작성
- [ ] `DefaultUnitPosition`에서 기본 좌표 처리에 대한 고민
