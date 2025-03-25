package direction;

public enum Movement {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    UP_UP_LEFT(-1, -2),
    UP_UP_RIGHT(1, -2),
    RIGHT_RIGHT_UP(2, -1),
    RIGHT_RIGHT_DOWN(2, 1),
    DOWN_DOWN_LEFT(-1, 2),
    DOWN_DOWN_RIGHT(1, 2),
    LEFT_LEFT_UP(-2, -1),
    LEFT_LEFT_DOWN(-2, 1)
    ;

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
