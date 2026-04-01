# Cycle1 Mission Record

현재 코드 기준으로 사이클1 미션 기록 항목을 정리했다. 각 항목은 실제 구현 코드 일부를 스니펫으로 인용해 근거를 남겼다.

## 1. 상태 위치를 결정할 때 고민한 순간 1회

규칙에 따른 말의 이동을 구현하기 위해서는 "현재 위치에서 말의 규칙을 따라 어디까지 갈 수 있는가"를 다룰 객체 `Route`가 필요했다. 이 고민은 `MoveStrategy.makeRoutes()`에 담겨있다.

단순히 방향만 나열하는 것이 아니라 다음 세 가지를 함께 다뤄야 했다.

- 현재 위치에서 한 단계씩 좌표를 진행할 것
- 중간 경로를 별도로 모을 것(장애물 여부에 따라 이동 가능성 체크)
- 보드 범위를 벗어나는 경로는 버릴 것

이 고민의 결과가 아래 코드다.

```java
default List<Route> makeRoutes(Position curPos, TeamColor teamColor) {
    List<Route> validRoutes = new ArrayList<>();
    List<MovePath> paths = getPaths(teamColor);

    for (MovePath path : paths) {
        List<Direction> steps = path.steps();
        Position currentPos = curPos;
        List<Position> intermediates = new ArrayList<>();

        for (int i = 0; i < steps.size(); i++) {
            try {
                currentPos = currentPos.next(steps.get(i));
            } catch (IllegalArgumentException exception) {
                currentPos = null;
                break;
            }

            if (i < steps.size() - 1) {
                intermediates.add(currentPos);
            }
        }
        if (currentPos == null) {
            continue;
        }
        validRoutes.add(new Route(curPos, currentPos, intermediates));
    }

    return validRoutes;
}
```

여기서는 "좌표 하나"가 아니라 "시작점, 도착점, 중간 경로"를 모두 가진 `Route`를 만들도록 설계를 밀어 올린 점이 중요했다.

## 2. 불변/캡슐화를 적용한 코드 1곳

불변과 캡슐화를 적용하기 위한 기준은

> 객체가 어떤 동작을 수행하기보다 값을 표현하는 역할이라면 불변 객체로 설계한다.

였다. 이 기준을 현재 코드에서 가장 잘 보여주는 예시는 `MovePath`, `Route`, `Row`, `Column`이다.

### record로 만든 값 객체

```java
public record MovePath(List<Direction> steps) {}
```

```java
public record Route(Position startPos, Position endPos, List<Position> intermeidateNodes) {}
```

`MovePath`와 `Route`는 "이동 규칙을 설명하는 값"이지, '스스로 상태를 변경하며 행동하는 객체'가 아니다. 그래서 `record`로 두고 setter 없이 불변 값처럼 사용했다.

Position도 record로 두고 필요한 메서드는 추가로 구현하면 되지 않을까 생각했으나, 공식 문서를 기반으로 "데이터만 담는 클래스"를 간결하게 만들기 위한 문법이라는 것을 확인하고 도메인 규칙을 알고 행동(보드 크기에 맞는 값으로 검증)해야 하는 Position은 클래스로 유지했다.


```java
public class Position {
    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }
}
```

`Position`도 내부 필드를 `final`로 두고, 직접 원시값을 받지 않고 `Row`, `Column`을 조합해서 생성한다는 점에서 값 객체 성격을 유지하고 있다.

## 3. 규칙 적용으로 변경한 설계 1곳

이번 미션에서 적용한 규칙 중 하나는

### 불변 객체 기준

- **If:** 객체가 어떤 동작을 수행하기보다 ‘값’을 표현하는 역할이라면  
  → **Then:** 해당 객체는 불변 객체로 설계한다.
- 기준
  - 생성 이후 상태가 변경되지 않아야 한다.
  - setter를 제공하지 않는다.
  - 모든 필드는 final로 선언하는 것을 기본으로 한다.

이 규칙을 적용하면서 `MovePath`, `Route`, `Row`, `Column`을 record/불변 객체로 유지하는 방향으로 설계를 잡았다.

특히 `Route`를 mutable 객체로 두고 이동 중간에 값을 채워 넣는 대신, 경로 계산이 끝났을 때 완성된 값으로 한 번에 생성했다.

```java
validRoutes.add(new Route(curPos, currentPos, intermediates));
```

이렇게 해두면 경로 판정 로직이 `Route`를 수정하지 않고 읽기만 하면 되어서, 이후 `Board`, `MoveStrategy`, `Piece` 협력에서도 데이터 흐름이 단순해진다.

## 4. 조건문을 다형성으로 대체한 코드 1곳

가장 대표적인 부분은 `MoveStrategy` 구조다. 기물별 이동 규칙을 `if-else`로 한 클래스에 몰아넣지 않고, 전략 객체로 분리했다.

`Piece`는 실제 이동 계산을 직접 하지 않고 `moveStrategy`에 위임한다.

```java
public class Piece {
    private final TeamColor teamColor;
    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;

    public List<Route> makeRoutes(Position from) {
        return moveStrategy.makeRoutes(from, teamColor);
    }

    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return moveStrategy.canMove(route, blockingPieces, destinationPiece, teamColor);
    }
}
```

기물별 규칙은 각 전략 클래스가 담당한다.

```java
public class PawnMoveStrategy implements MoveStrategy {
    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return List.of(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.EAST)),
                    new MovePath(List.of(Direction.WEST))
            );
        }

        return List.of(
                new MovePath(List.of(Direction.SOUTH)),
                new MovePath(List.of(Direction.EAST)),
                new MovePath(List.of(Direction.WEST))
        );
    }
}
```

```java
public class RookMoveStrategy implements MoveStrategy {
    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        List<MovePath> paths = new ArrayList<>();
        addStraightPaths(paths, Direction.NORTH);
        addStraightPaths(paths, Direction.SOUTH);
        addStraightPaths(paths, Direction.EAST);
        addStraightPaths(paths, Direction.WEST);
        return paths;
    }
}
```

아직 `Piece.createMoveStrategy()` 내부에는 `PieceType`에 따른 조건문이 남아 있다. 다만 핵심 이동 규칙 자체는 `MoveStrategy` 다형성으로 분리되어 있고, 실제 행동 차이는 각 구현체가 맡고 있다는 점에서 "한 곳에서 모든 규칙을 조건문으로 처리하던 구조"보다는 훨씬 나아졌다.

## 5. 인터페이스/추상클래스를 도입한 이유

### MoveStrategy 인터페이스를 도입한 이유

기물마다 이동 방식은 다르지만, 외부에서 볼 때 공통적인 동작이 존재했다.

- 이동 경로를 만든다.
- 현재 보드 상태를 기준으로 실제 이동 가능 여부를 판단한다.

이 공통으로 이루어지는 동작을 `MoveStrategy` 인터페이스로 추상화 했다.

```java
public interface MoveStrategy {

    List<MovePath> getPaths(TeamColor teamColor);

    default List<Route> makeRoutes(Position curPos, TeamColor teamColor) { ... }

    default boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return destinationPiece.isEmpty() || destinationPiece.get().getTeamColor() != myTeam;
    }
}
```

이렇게 해두면 `Piece`는 "이 말이 졸인지 차인지"를 몰라도 된다. 그냥 `moveStrategy.makeRoutes(...)`, `moveStrategy.canMove(...)`만 호출하면 된다.

여기서 기물 종류가 늘어나더라도 `Piece`가 협력하는 방식은 유지할 수 있다.

### InitialFormationStrategy 추상클래스를 도입한 이유

상차림 선택지마다 달라지는 것은 상,마의 배치 뿐이고, 이 두 기물을 제외하고 공통으로 고정되는 배치는 같았다.

이 공통 부분을 추상클래스로 끌어올렸다.

```java
public abstract class InitialFormationStrategy {

    public final Map<Position, Piece> setUpPieces(TeamColor teamColor) {
        Map<Position, Piece> formationPieces = setupFormation(teamColor);
        Map<Position, Piece> fixedPieces = placeFixedPieces(teamColor);
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.putAll(formationPieces);
        allPieces.putAll(fixedPieces);

        return allPieces;
    }

    protected abstract Map<Position, Piece> setupFormation(TeamColor teamColor);
}
```

그리고 실제 차이는 하위 클래스에서만 구현하도록 했다.

```java
public class InnerFormationStrategy extends InitialFormationStrategy {
    @Override
    protected Map<Position, Piece> setupFormation(TeamColor teamColor) {
        Map<Position, Piece> formation = new HashMap<>();
        int row;
        if (teamColor.equals(TeamColor.CHO)) {
            row = 9;
            formation.put(Position.of(row, 1), Piece.of(teamColor, PieceType.HORSE));
            formation.put(Position.of(row, 2), Piece.of(teamColor, PieceType.ELEPHANT));
            ...
        }
        ...
        return formation;
    }
}
```

"공통 초기화 절차"를 고정하고, 전략별 차이만 하위 클래스에 위임하도록 추상 클래스를 도입하였다고 할 수 있다.

## 6. 새 기물 추가 시 변경 범위 테스트(가상)

현재 `PieceType`에 새로운 기물을 하나 추가한다고 가정하면, 지금 구조에서는 변경 범위가 크지 않다.

우선 새 `PieceType` 값을 추가하고, 해당 기물의 이동 규칙을 담을 `MoveStrategy` 구현체를 만든다. 그 다음 `Piece.createMoveStrategy()`에만 전략 연결을 추가하면 `Piece -> MoveStrategy -> Route` 흐름 안으로 기능을 구현할 수 있다.

초기 배치에 포함해야 한다면 `InitialFormationStrategy` 계열에서 좌표만 추가하면 되고, 테스트는 "경로 생성 테스트", "차단/포획 판정 테스트", "초기 배치 테스트" 에 새 기물에 대한 케이스를 추가하면 된다.

지금 구조에서는 새 기물 추가 시 수정 지점이 완전히 없지는 않지만, "이동 규칙", "전략 연결", "초기 배치", "테스트"로 범위를 예측 가능하다.