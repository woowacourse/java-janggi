# 설계

### 기능의 흐름
1. 사용자로부터 src와 destination을 입력받는다.
2. Board는 src에 존재하는 기물에게 destination으로 갈 수 있는지 물어본다.
3. 만약 기물이 이동할 수 있다면 Board는 이동시켜준다.
4. 사용자에게 현재 보드의 상태를 보여준다.

## board 패키지
 
### Board  
Board 클래스는 기물의 위치를 관리하는 책임을 가지고 있습니다. 

### PieceInitialPosition
기물들의 초기 위치를 관리합니다.

### Position
Board의 위치를 관리합니다.

## piece 패키지
모든 기물들의 클래스들이 존재합니다.

### RelativePosition
상대 경로를 관리하는 책임을 가집니다.
- 상, 하, 좌, 우, 대각선들의 경로를 가집니다.

### Path
상대 경로들의 일급 콜렉션으로 상대 경로들에 대한 계산을 담당합니다.
