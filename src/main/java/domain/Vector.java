package domain;

public enum Vector {

    UP(0, 1),
    DOWN(0, -1),
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
}
