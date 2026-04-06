package janggi.domain;

public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    WEST(-1, 0),
    EAST(1, 0),
    NORTH_WEST(-1, -1),
    NORTH_EAST(1, -1),
    SOUTH_WEST(-1, 1),
    SOUTH_EAST(1, 1),
    ;

    private final int column;
    private final int row;

    Direction(int column, int row) {
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
