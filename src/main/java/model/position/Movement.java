package model.position;

public enum Movement {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0,1),
    DIAGONAL_UP_LEFT(-1, -1),
    DIAGONAL_UP_RIGHT(-1, 1),
    DIAGONAL_DOWN_LEFT(1, -1),
    DIAGONAL_DOWN_RIGHT(1, 1),

    UP_AND_DIAGONAL_UP_LEFT(-2, -1),
    UP_AND_DIAGONAL_UP_RIGHT(-2, 1),
    DOWN_AND_DIAGONAL_DOWN_LEFT(2, -1),
    DOWN_AND_DIAGONAL_DOWN_RIGHT(2, 1),
    LEFT_AND_DIAGONAL_DOWN_LEFT(1, -2),
    LEFT_AND_DIAGONAL_UP_LEFT(-1, -2),
    RIGHT_AND_DIAGONAL_DOWN_RIGHT(1, 2),
    RIGHT_AND_DIAGONAL_UP_RIGHT(-1, 2),

    UP_AND_DOUBLE_DIAGONAL_UP_LEFT(-3, -2),
    UP_AND_DOUBLE_DIAGONAL_UP_RIGHT(-3, 2),
    DOWN_AND_DOUBLE_DIAGONAL_DOWN_LEFT(3, -2),
    DOWN_AND_DOUBLE_DIAGONAL_DOWN_RIGHT(3, 2),
    LEFT_AND_DOUBLE_DIAGONAL_DOWN_LEFT(2, -3),
    LEFT_AND_DOUBLE_DIAGONAL_UP_LEFT(-2, -3),
    RIGHT_AND_DOUBLE_DIAGONAL_DOWN_RIGHT(2, 3),
    RIGHT_AND_DOUBLE_DIAGONAL_UP_RIGHT(-2, 3);

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
