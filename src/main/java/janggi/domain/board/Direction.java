package janggi.domain.board;

import java.util.Arrays;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1),
    ;

    private final int x;

    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Direction from(int rowDirection, int colDirection) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.x == rowDirection && direction.y == colDirection)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 방향이 없습니다."));
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
