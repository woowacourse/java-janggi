# java-janggi

장기 미션 저장소

# 기능 요구사항

## 보드 초기화 기능
- [x] 장기 기물을 정해진 개수만큼 생성할 수 있다.
  - [x] `궁`은 블루팀/레드팀이 1개씩 가짐
  - [x] `사`는 블루팀/레드팀이 2개씩 가짐
  - [x] `차`는 블루팀/레드팀이 2개씩 가짐
  - [x] `포`는 블루팀/레드팀이 2개씩 가짐
  - [x] `마`는 블루팀/레드팀이 2개씩 가짐
  - [x] `상`은 블루팀/레드팀이 2개씩 가짐
  - [x] `병`은 블루팀/레드팀이 5개씩 가짐
- [x] 장기판을 생성할 수 있다.
  - [x] `블루팀`은 남쪽, `레드팀`은 북쪽에 배치
  - [x] 각 기물을 고정된 위치에 배치

## 장기 기물 이동
- [ ] 이동할 기물의 이름과 목표 위치를 입력할 수 있다.
- [ ] 이동하고자 하는 위치로 움직일 수 있는지 확인한다.
  - [x] 기물의 가동 범위에 속하는지 판단
    - [ ] 가동 범위에 속하지 않는 목표 위치라면 예외
    - [ ] 장기판의 범위를 넘는다면 예외
  - [ ] 기물의 이동 경로에 장애물이 있는지 판단
    - [ ] 이동 경로에 기물이 있는 경우 예외
    - [ ] 최종 목적지에 우리 팀의 기물이 있는 경우 예외
    - [ ] 최종 목적지에 상대 팀의 기물이 있는 경우에는 **공격 기능으로 이동**
- [ ] 기물을 최종 목적지에 이동시킬 수 있다.

## 장기 기물 공격
- [ ] 기물이 이동한 위치에 있는 기물을 공격할 수 있다.
- [ ] 공격받은 기물은 보드 상 기물(`pieces`)에서 제외된다.
