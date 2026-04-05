package janggi.domain.piece.direction;

import java.util.Arrays;

public enum MaDirection {
    UP_LEFT(
        new Offset(-1, -2),
        new Offset(0, -1)
    ),
    UP_RIGHT(
        new Offset(1, -2),
        new Offset(0, -1)
    ),
    DOWN_LEFT(
        new Offset(-1, 2),
        new Offset(0, 1)
    ),
    DOWN_RIGHT(
        new Offset(1, 2),
        new Offset(0, 1)
    ),
    LEFT_UP(
        new Offset(-2, -1),
        new Offset(-1, 0)
    ),
    LEFT_DOWN(
        new Offset(-2, 1),
        new Offset(-1, 0)
    ),
    RIGHT_UP(
        new Offset(2, -1),
        new Offset(1, 0)
    ),
    RIGHT_DOWN(
        new Offset(2, 1),
        new Offset(1, 0)
    ),
    ;

    private final Offset target;
    private final Offset route;

    MaDirection(Offset target, Offset route) {
        this.target = target;
        this.route = route;
    }

    public static MaDirection find(int directionCol, int directionRow) {
        return Arrays.stream(values())
                .filter(dir -> dir.target.directionColumn() == directionCol && dir.target.directionRow() == directionRow)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 마가 이동할 수 없는 방향입니다."));
    }

    public Offset getWaypoint() {
        return route;
    }
}
