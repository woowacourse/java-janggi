# java-janggi

장기 미션 저장소

## 장기 게임 진행
- [x] 입력에 오류 있는 경우, 예외 메시지를 출력한 후 재입력받는다.
- [x] 초, 한의 순서로 번갈아가며 말을 이동한다.
- [ ] 궁이 잡힌 팀이 패배한다.

## 보드판

- [x] 가로는 9줄, 세로는 10줄로 이루어져 있다.
- [x] 마와 상을 제외한 장기 기물의 초기 위치를 저장한다.
    - [x] 기물이 존재하지 않는 위치도 초기화한다.
- [x] 모든 말들의 위치를 알고있다.
- [x] 보드판 밖을 벗어날 수 없다.

## 장기의 두 진영

- [x] 궁 1개, 사 2개, 마 2개, 상 2개, 졸/병 5개, 포 2개, 차 2개로 구성되어 있다.

## 장기 기물
 
- [x] 한나라, 초나라로 구분된다.
- [x] 각 기물은 규칙에 따라 움직일 수 있다.
- [x] 포를 제외한 기물은 다른 기물을 뛰어넘을 수 없다.
- [x] 각자의 생존 여부를 알고있다.
- [x] 목적지에 같은 팀의 기물이 있는 경우 움직일 수 없다.
- [x] 다른 기물을 잡을 수 있다.

| 장기 기물 | 가능한 이동 경로                      | 점수 | 특이 사항                                            |
|-------|--------------------------------|----|--------------------------------------------------|
| 궁     | 상, 하, 좌, 우 1칸                  | -  | -                                                |
| 사     | 상, 하, 좌, 우 1칸                  | 3  | -                                                |
| 마     | 상, 하, 좌, 우로 1칸 이동한 후, 대각선으로 1칸 | 5  | -                                                |
| 상     | 상, 하, 좌, 우로 1칸 이동한 후, 대각선으로 2칸 | 3  | -                                                |
| 졸병    | 상, 좌, 우로 1칸                    | 2  | 나라에 따라 이동 방향이 다르다.                               |
| 포     | 수직, 수평 여러 칸                    | 7  | 장애물이 1개 있어야 이동할 수 있고, 장애물과 잡을 기물이 모두 포가 아니어야 한다. |
| 차     | 수직, 수평 여러 칸                    | 13 | -                                                |
