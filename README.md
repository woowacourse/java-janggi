# java-janggi

장기 미션 저장소

## 기능 요구사항

### 1-1. 보드 초기화

협력명: 장기판을 초기화 한다.

필요한 메시지(행동)
- 장기판을 초기화 하라
- 각 진영별 배치를 초기화 하라
- 진영별 배치를 선택하라
- 선택한 배치대로 기물들을 배치하라

#### 시나리오
1. 장기 게임을 초기화 한다. - JanggiGame
2. 진영별 배치를 선택한다. - Player
3. 진영에 맞는 기물 타입의 기물을 생성한다. - Piece
4. 각 진영별 기물 배치를 초기화한다. - Board

#### 각 메시지(행동)에 따른 책임을 부여한 객체
#### JanggiGame
- [ ] 장기 게임을 초기화 한다.
- Board
- Players

#### Player
- [x] 진영별 배치를 선택한다.
- 진영 side ENUM

#### Piece
- [x] 기물 타입의 기물을 생성한다.
- 진영 side ENUM
- 기물 종류 type ENUM

#### Board
- [x] 각 진영별 기물 배치를 초기화한다.
- Map<Position, Piece>
- MAX_ROW
- MIN_ROW
- MAX_COLUMN
- MIN_COLUMN

#### Position
- row
- column
