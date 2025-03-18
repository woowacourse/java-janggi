# 기능 요구 사항

## 위치

- 위치는 파일과 랭크로 구분된다.
- 파일과 랭크가 같으면 동일한 위치이다.

### 파일

- 파일은 `가` 부터 `자`까지 존재한다.

### 랭크

- 랭크는 1 부터 10까지 존재한다.
- 해당 범위 외의 값으로 생성하려고 하면 예외가 발생한다.
- 랭크는 값이 같으면 동일하다.
- 랭크는 한나라, 초나라 관점에서 모두 생성할 수 있다.

## 보드

- 보드는 두 팀으로 구성된다.
- 두 팀은 한나라와 초나라여야 한다.

## 팀

- 팀은 초나라 혹은 한나라의 피스들로 이루어진다.
- 현재 피스들의 위치를 반환할 수 있다.
- 마상마상 / 상마상마 등의 시작 위치를 통해 생성될 수 있다.

### 시작 위치

- 시작위치는 다음의 종류가 존재한다.
  - 상마상마
  - 마상마상
  - 마상상마
  - 상마마상

## 피스

### 피스 종류

- 피스 종류는 다음의 종류가 존재한다.
  - 장
  - 사
  - 차
  - 마
  - 상
  - 포
  - 졸
