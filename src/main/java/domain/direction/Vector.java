package domain.direction;

import java.util.List;

public enum Vector {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_RIGHT(RIGHT.row, UP.column),
    UP_LEFT(LEFT.row, UP.column),
    DOWN_RIGHT(RIGHT.row, DOWN.column),
    DOWN_LEFT(LEFT.row, DOWN.column),
    ;

    private final int row;
    private final int column;

    Vector(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static List<Vector> getUpDownRightLeft() {
        return List.of(UP, DOWN, LEFT, RIGHT);
    }

    public static List<Vector> getDiagonals() {
        return List.of(UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT);
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }
}
