package janggi.domain.movement;

public enum Direction {

    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1),
    NORTH_WEST(-1, -1);

    private final int row;
    private final int column;

    Direction(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public int getRowDirection() {
        return row;
    }

    public int getColumnDirection() {
        return column;
    }
}
