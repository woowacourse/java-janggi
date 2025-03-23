package janggi.position;

public enum Direction {
    EAST(1, 0),
    WEST(-1, 0),
    SOUTH(0, -1),
    NORTH(0, 1),

    NORTH_EAST(1, 1),
    SOUTH_EAST(1, -1),
    SOUTH_WEST(-1, -1),
    NORTH_WEST(-1, 1),

    EAST_NORTH(1, 1),
    EAST_SOUTH(1, -1),
    WEST_SOUTH(-1, -1),
    WEST_NORTH(-1, 1);

    private final int row;
    private final int column;

    Direction(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }
}
