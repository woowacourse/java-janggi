package janggi.domain.piece.direction;

import java.util.Arrays;
import java.util.List;

public enum MaDirection {
    UP_LEFT(
        List.of(
            new Offset(-1, -2),
            new Offset(0, -1)
        )
    ),
    UP_RIGHT(
        List.of(
            new Offset(1, -2),
            new Offset(0, -1)
        )
    ),
    DOWN_LEFT(
        List.of(
            new Offset(-1, 2),
            new Offset(0, 1)
        )
    ),
    DOWN_RIGHT(
        List.of(
            new Offset(1, 2),
            new Offset(0, 1)
        )
    ),
    LEFT_UP(
        List.of(
            new Offset(-2, -1),
            new Offset(-1, 0)
        )
    ),
    LEFT_DOWN(
        List.of(
            new Offset(-2, 1),
            new Offset(-1, 0)
        )
    ),
    RIGHT_UP(
        List.of(
            new Offset(2, -1),
            new Offset(1, 0)
        )
    ),
    RIGHT_DOWN(
        List.of(
            new Offset(2, 1),
            new Offset(1, 0)
        )
    );

    private final List<Offset> routes;

    MaDirection(List<Offset> routes) {
        this.routes = routes;
    }

    public static MaDirection find(int directionCol, int directionRow) {
        return Arrays.stream(values())
                .filter(dir -> dir.getTarget().directionColumn() == directionCol && dir.getTarget().directionRow() == directionRow)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 마가 이동할 수 없는 방향입니다."));
    }

    public Offset getWaypoint() {
        return routes.getLast();
    }

    private Offset getTarget() {
        return routes.getFirst();
    }
}
