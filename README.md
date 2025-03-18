# 장기 게임

## 학습 목표

**OOP - Object-Oriented Programming**

- OOP의 개념과 원리 이해하기
- 좋은 객체지향 설계의 원칙 적용하기
- 객체지향 설계 능력 향상을 위한 실천 방법 습득하기

## 설계 방법

1. 도메인 분석 및 정리
2. 협력 관계 명세 (기능 목록 작성)
3. 책임 기반 세부 명세 (기능 세분화)
4. 메시지 명 정의 (메서드 네이밍)
5. 역할 구체화 후 인터페이스 설계
6. TDD(테스트 주도 개발) 기반 구현

## 기능 목록 (협력 관계 명세)

> 각 책임은 "~는 ~해야 한다" 형식으로 명시

- 게임은 한나라(빨간색)와 초나라(파랑색) 두 팀으로 나뉘어야 한다
- 한나라가 선공권을 가져야 한다
- 보드는 9개의 수직선과 10개의 수평선으로 구성되어야 한다
- 상과 마를 제외한 기물들은 고정된 초기 위치를 가져야 한다
- 플레이어는 경기 시작 전 상과 마의 위치를 선택할 수 있어야 한다
- 플레이어는 장기 기물을 이동시킬 수 있어야 한다
- 플레이어는 상대 기물을 잡을 수 있어야 한다
- 각 기물은 정해진 이동 방식에 따라 보드 내에서만 움직여야 한다
- 왕이 잡히면 게임이 종료되어야 한다

## 책임 기반 세부 명세

### 1. 팀 구성

- [ ] 플레이어는 팀을 선택할 수 있어야 한다
- [ ] 팀은 색상(한나라:빨강, 초나라:파랑)으로 구별되어야 한다
- [ ] 한나라는 선공권을 가져야 한다

### 2. 보드 구성

- [ ] 보드는 기물들의 위치를 관리해야 한다
- [ ] 보드는 9x10 크기로 구성되어야 한다
- [ ] 보드는 기물의 이동 범위를 제한해야 한다

### 3. 기물 배치

- [ ] 기물들은 정해진 초기 위치를 가져야 한다
- [ ] 플레이어는 상과 마의 초기 배치를 선택할 수 있어야 한다
- [ ] 보드는 기물들의 배치 위치를 추적해야 한다
- [ ] 기물은 초기 배치 변경 가능 여부를 나타내야 한다

### 4. 기물 이동

- [ ] 플레이어는 이동시킬 기물의 위치를 선택해야 한다 (출발점)
- [ ] 플레이어는 기물이 이동할 위치를 지정해야 한다 (도착점)
- [ ] 보드는 출발점이 보드 내에 있는지 확인해야 한다
- [ ] 보드는 출발점에 기물이 있는지 확인해야 한다
- [ ] 보드는 도착점이 보드 내에 있는지 확인해야 한다
- [ ] 기물은 도착점으로 이동 가능한지 확인해야 한다
- [ ] 보드는 이동 후 도착점의 기물을 처리해야 한다

### 5. 기물별 이동 규칙

- [ ] 각 기물은 고유한 이동 방식을 가져야 한다
- [ ] 각 기물은 이동 가능 여부를 판단해야 한다

#### 공통 이동 제한 사항

- [ ] 경로 상에 다른 기물이 있을 때 이동할 수 없다 (포 제외)
- [ ] 자신의 이동 방식으로 도착할 수 없는 위치로 이동할 수 없다

#### 포의 이동 제한 사항

- [ ] 포는 이동 경로에 기물이 하나도 없으면 이동할 수 없다
- [ ] 포는 두 개 이상의 기물을 넘어 이동할 수 없다
- [ ] 포는 다른 포를 넘어 이동할 수 없다

### 6. 게임 종료 조건

- [ ] 왕이 잡히면 해당 팀이 패배해야 한다
