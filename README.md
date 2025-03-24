# **java-janggi**

장기 미션 저장소

### **기능 요구사항**

- board
  - [x]  초기 Board를 생성한다.
  - [x]  기물 위치 업데이트
- TeamType
- piece
  - [x]  각 기물 추상화하기
  - Cannon
    - [x]  직선으로 앞의 기물이 존재한다면 뛰어넘을 수 있음
    - [x]  도착지까지 사이에 기물이 하나여야 함
    - [x]  포는 포를 넘을 수 없고 포를 잡을 수도 없음
  - Chariot
    - [x]  동서남북 직선
  - Elephant
    - [x]  직-대-대 방향 이동
  - General
    - [x]  한칸 이동
  - Guard
    - [x]  한칸 이동
  - Horse
    - [x]  직 - 대 이동
  - Soldier
    - [x]  한칸 이동
    - [x]  뒤로 못감
