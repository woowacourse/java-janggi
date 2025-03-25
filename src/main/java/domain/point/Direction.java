package domain.point;

import java.util.List;

public enum Direction {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1),
    ;

    public static final List<Direction> VERTICALS = List.of(UP, DOWN, LEFT, RIGHT);
    public static final List<Direction> DIAGONALS = List.of(UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT);

    private final int deltaRow;
    private final int deltaColumn;

    Direction(final int deltaRow, final int deltaColumn) {
        this.deltaRow = deltaRow;
        this.deltaColumn = deltaColumn;
    }

    public int deltaRow() {
        return deltaRow;
    }

    public int deltaColumn() {
        return deltaColumn;
    }

    public Direction inverse() {
        if (this == UP) {
            return DOWN;
        }
        if (this == UP_RIGHT) {
            return DOWN_LEFT;
        }
        if (this == RIGHT) {
            return LEFT;
        }
        if (this == DOWN_RIGHT) {
            return UP_LEFT;
        }
        if (this == DOWN) {
            return UP;
        }
        if (this == DOWN_LEFT) {
            return UP_RIGHT;
        }
        if (this == LEFT) {
            return RIGHT;
        }
        if (this == UP_LEFT) {
            return DOWN_RIGHT;
        }
        throw new IllegalStateException("도달 할 수 없는 상태입니다.");
    }
}
