# java-janggi

장기 미션 저장소

### 초기화

- [x] 장기판에 말들에 대한 초기 배치 상태를 결정한다.
- [x] 장기판에 초기 배치 상태에 맞게 모든 말을 생성한다.

### 이동

- [x] 좌표를 받아 이동할 수 있는지 확인한다.
- [ ] 보드에서 말의 위치를 바꾼다.

### 고려해야할 것

- ✅ 장기의 말(駒)들은 어떻게 객체로 나누어야 하는가?
    - 장기의 말(駒)이라는 추상 객체를 구현하는 구현체를 통해 나눈다.
- ✅ 각 말의 이동 규칙을 어떻게 설계할 것인가?
    - 장기의 말의 구현체의 책임으로 설계한다.
- ✅ 객체 간의 책임을 어떻게 나눌 것인가?
    - 아는 것과 하는 것을 분리하여 가장 전문가인 객체에게 책임을 맡긴다.
- ✅ 어떤 원칙을 적용해야 더 좋은 설계를 할 수 있을까?
    - 객체지향 생활체조 원칙을 적용한다.
