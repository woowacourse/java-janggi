# java-janggi

장기 미션 저장소

## 과제 진행 요구 사항

첫 시작시의 장기의 보드판 그리고 각 기물을 구현하라
게임 시작 시 전체 기물들과 장기판을 올바른 위치에 초기화 한다.

## 기능 요구사항

- [ ] 기물(piece) 종류
    - 궁(宮): Palace (1개)
        - 이동 가능 방향: [-1, 0], [0, +1], [+1, 0], [0, -1] (추가 구현 예정)
    - 차(車): Chariot (2개)
        - 이동 가능 방향: [0, +n]
        - 이동 가능 방향: [0, -n]
        - 이동 가능 방향: [+n, 0]
        - 이동 가능 방향: [-n, 0]
    - 포(包): Pao (2개)
        - 이동 가능 방향: 다른 기물을 지나 -> [0, +n]
        - 이동 가능 방향: 다른 기물을 지나 -> [0, -n]
        - 이동 가능 방향: 다른 기물을 지나 -> [+n, 0]
        - 이동 가능 방향: 다른 기물을 지나 -> [-n, 0]
    - 마(馬): Horse (2개)
        - 이동 가능 방향: [0, +1] -> [+1, +1]
        - 이동 가능 방향: [0, +1] -> [-1, +1]
        - 이동 가능 방향: [0, -1] -> [-1, -1]
        - 이동 가능 방향: [0, -1] -> [+1, -1]
        - 이동 가능 방향: [-1, 0] -> [-1, +1]
        - 이동 가능 방향: [-1, 0] -> [-1, -1]
        - 이동 가능 방향: [+1, 0] -> [+1, +1]
        - 이동 가능 방향: [+1, 0] -> [+1, -1]
    - 상(象): Elephant (2개)
        - 이동 가능 방향: [0, +1] -> [+1, +1] -> [+1, +1]
        - 이동 가능 방향: [0, +1] -> [-1, +1] -> [-1, +1]
        - 이동 가능 방향: [0, -1] -> [-1, -1] -> [-1, -1]
        - 이동 가능 방향: [0, -1] -> [+1, -1] -> [+1, -1]
        - 이동 가능 방향: [-1, 0] -> [-1, +1] -> [-1, +1]
        - 이동 가능 방향: [-1, 0] -> [-1, -1] -> [-1, -1]
        - 이동 가능 방향: [+1, 0] -> [+1, +1] -> [+1, +1]
        - 이동 가능 방향: [+1, 0] -> [+1, -1] -> [+1, -1]
    - 사(士): Soldier (2개)
      - 이동 가능 방향: [-1, 0], [0, +1], [+1, 0], [0, -1] (추가 구현 예정) 
    - 병(兵): Pawn (5개)
      - 이동 가능 방향: [-1, 0], [0, +1], [+1, 0]
    - [ ] 입출력 요구사항
      - [ ] 진영에 따라 말 폰트 색을 다르게 한다.
        - [ ] 한나라는 빨강(`\u001B[31m`), 초나라는 파랑(`\u001B[34m`)으로 표시한다.
        
- [ ] 장기판
    - [ ] 9(가로) x 10(세로) 크기의 장기판을 사용한다.
    - [ ] 한 칸에는 하나의 기물만 올 수 있다.
    - [ ] 입출력 요구사항
        - [ ] 현재 장기판 현황을 출력한다.
        - [ ] 각 칸은 괘선문자 + 띄어쓰기로 이루어진다.
        - [ ] 말이 존재할 경우 띄어쓰기없이 표기한다.

```
// 장기판 모양
  i h g f e d c b a
1  ┌ ┬ ┬ ┬ ┬ ┬ ┬ ┬ ┐
2  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
3  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
4  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
5  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
6  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
7  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
8  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
9  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
10 └ ┴ ┴ ┴ ┴ ┴ ┴ ┴ ┘

// 초기 세팅 모습
  i h g f e d c b a
1  車馬象士┬ 士象馬車
2  ├ ┼ ┼ ┼ 宮┼ ┼ ┼ ┤
3  ├ 包┼ ┼ ┼ ┼ ┼ 包┤
4  兵┼ 兵┼ 兵┼ 兵┼ 兵
5  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
6  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
7  兵┼ 兵┼ 兵┼ 兵┼ 兵
8  ├ 包┼ ┼ ┼ ┼ ┼ 包┤
9  ├ ┼ ┼ ┼ 宮┼ ┼ ┼ ┤
10 車馬象士┴ 士象馬車
```

- [ ] 기물 이동
    - [ ] 기물은 대상 칸으로 이동할 수 있다.
    - [ ] 기물이 이동할 수 없는 칸으로 이동을 시도하면 예외를 반환한다.
      - [ ] 기물의 이동 경로에 다른 기물이 존재할 경우
        - [ ] 포는 예외적으로 이동 경로에 다른 기물이 존재해야 이동할 수 있다.
      - [ ] 한 칸에 같은 팀의 기물이 이동할 경우
      - [ ] 장기판 밖으로 나갈 경우
  - [ ] 입출력 요구사항
      - [ ] 기물이 이동 가능한 칸을 화면에 출력한다.

- [ ] 기물 잡기 규칙
  - [ ] 한 칸에서 다른 팀의 기물과 만나면 나중에 온 기물이 기존 기물을 잡는다.

- [ ] 게임 전체 규칙
- [ ] 외통수일 경우(아래의 조건을 모두 만족할 때) 게임이 종료된다.
  - [ ] 궁의 이동으로 장군을 피할 수 없는 경우
  - [ ] 궁이 아닌 다른 기물의 이동으로 장군을 피할 수 없는 경우
    - [ ] 장군을 건 기물의 경로를 다른 기물로 막을 수 없는 경우
    - [ ] 장군을 건 기물을 잡을 수 없는 경우
- [ ] 플레이어는 한 턴에 한 번 게임 종료 요청을 할 수 있다.
  - [ ] 상대 플레이어가 수락할 시 게임이 종료되며, 남아있는 기물 점수를 종합하여 승패를 결정한다.

- [ ] 게임 진행 입출력 요구사항
  - [ ] 턴 전환 시
    - [ ] 현재 장기판 모습을 출력한다.
    - [ ] 현재 누구의 차례인지 출력한다.
      - [ ] `초나라` or `한나라`에 해당하는 색을 입한다.
  - [ ] 기물 이동
    - [ ] 이동할 기물의 현재 위치와 이동할 대상 위치를 좌표로 입력받는다.
    - [ ] 기물 이동이 실패할 경우 예외 메시지를 출력한 뒤 재입력을 요청한다.
    - [ ] 기물 이동을 완료하면 상대방의 턴으로 전환된다.
  
```
장기 게임을 시작합니다.

  i h g f e d c b a
1  車馬象士┬ 士象馬車
2  ├ ┼ ┼ ┼ 宮┼ ┼ ┼ ┤
3  ├ 包┼ ┼ ┼ ┼ ┼ 包┤
4  兵┼ 兵┼ 兵┼ 兵┼ 兵
5  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
6  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
7  兵┼ 兵┼ 兵┼ 兵┼ 兵
8  ├ 包┼ ┼ ┼ ┼ ┼ 包┤
9  ├ ┼ ┼ ┼ 宮┼ ┼ ┼ ┤
10 車馬象士┴ 士象馬車

초나라의 차례입니다.

이동할 기물의 현재 위치와 이동할 대상 위치를 좌표로 입력해주세요.(ex. i1 i2)
i1 i2

  i h g f e d c b a
1  ┌ 馬象士┬ 士象馬車
2  車┼ ┼ ┼ 宮┼ ┼ ┼ ┤
3  ├ 包┼ ┼ ┼ ┼ ┼ 包┤
4  兵┼ 兵┼ 兵┼ 兵┼ 兵
5  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
6  ├ ┼ ┼ ┼ ┼ ┼ ┼ ┼ ┤
7  兵┼ 兵┼ 兵┼ 兵┼ 兵
8  ├ 包┼ ┼ ┼ ┼ ┼ 包┤
9  ├ ┼ ┼ ┼ 宮┼ ┼ ┼ ┤
10 車馬象士┴ 士象馬車
```

- [ ] 추후 구현(1단계)
  - [ ] 이동 가능한 기물이 하나도 없을 경우 플레이어는 턴을 쉬어야 하고, 다음 플레이어로 차례가 넘어간다.
  - [ ] 게임 시작 시 플레이어에게 상차림(Table Setting) 선택지를 제공한다.

- [ ] 차후 단계에서 구현할 내용(2.1단계)
    - [ ] 궁(宮) 또는 궁성(宮城)의 영역을 구현한다.
    - [ ] 장기판의 왕(將, 帥)과 사(士, 仕)의 이동과 관련된 규칙은 고려하지 않는다.
    - [ ] 기물의 점수를 계산하여 승패를 계산한다.
      - [ ] 기물의 점수를 활용한 승패는 무승부에서만 적용한다.
      - [ ] 승패와 상관없이 기물의 점수는 항상 노출한다.
