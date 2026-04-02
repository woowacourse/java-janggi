package janggi.domain.piece.direction;

import java.util.Arrays;
import java.util.List;

public enum SangDirection {
    UP_LEFT(
        List.of(
            new Offset(-2, -3),
            new Offset(0, -1),
            new Offset(-1, -2)
        )
    ),
    UP_RIGHT(
        List.of(
            new Offset(2, -3),
            new Offset(0, -1),
            new Offset(1, -2)
        )
    ),
    DOWN_LEFT(
        List.of(
            new Offset(-2, 3),
            new Offset(0, 1),
            new Offset(-1, 2)
        )
    ),
    DOWN_RIGHT(
        List.of(
            new Offset(2, 3),
            new Offset(0, 1),
            new Offset(1, 2)
        )
    ),
    LEFT_UP(
        List.of(
            new Offset(-3, -2),
            new Offset(-1, 0),
            new Offset(-2, -1)
        )
    ),
    LEFT_DOWN(
        List.of(
            new Offset(-3, 2),
            new Offset(-1, 0),
            new Offset(-2, 1)
        )
    ),
    RIGHT_UP(
        List.of(
            new Offset(3, -2),
            new Offset(1, 0),
            new Offset(2, -1)
        )
    ),
    RIGHT_DOWN(
        List.of(
            new Offset(3, 2),
            new Offset(1, 0),
            new Offset(2, 1)
        )
    );

    private final List<Offset> routes;

    SangDirection(List<Offset> routes) {
        this.routes = routes;
    }

    public static SangDirection find(int directionCol, int directionRow) {
        return Arrays.stream(values())
                .filter(dir -> dir.getTarget().directionColumn() == directionCol
                        && dir.getTarget().directionRow() == directionRow)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 상이 이동할 수 없는 방향입니다."));
    }

    public List<Offset> getWaypoints() {
        return routes.subList(1, routes.size());
    }

    private Offset getTarget() {
        return routes.getFirst();
    }
}
