package model;

public enum Movement {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0,1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1),

    UP_UP_LEFT(-2, -1),
    UP_UP_RIGHT(-2, 1),
    DOWN_DOWN_LEFT(2, -1),
    DOWN_DOWN_RIGHT(2, 1),
    LEFT_DOWN_LEFT(1, -2),
    LEFT_UP_LEFT(-1, -2),
    RIGHT_DOWN_RIGHT(1, 2),
    RIGHT_UP_RIGHT(-1, 2),

    UP_UP_LEFT_UP_LEFT(-3, -2),
    UP_UP_RIGHT_UP_RIGHT(-3, 2),
    DOWN_DOWN_LEFT_DOWN_LEFT(3, -2),
    DOWN_DOWN_RIGHT_DOWN_RIGHT(3, 2),
    LEFT_DOWN_LEFT_DOWN_LEFT(2, -3),
    LEFT_UP_LEFT_UP_LEFT(-2, -3),
    RIGHT_DOWN_RIGHT_DOWN_RIGHT(2, 3),
    RIGHT_UP_RIGHT_UP_RIGHT(-2, 3);

    private final int column;
    private final int row;

    Movement(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
