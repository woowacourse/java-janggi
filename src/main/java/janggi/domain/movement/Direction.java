package janggi.domain.movement;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_RIGHT(-1, 1),
    UP_LEFT(-1, -1),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(1, -1);

    private final int rowDirection;
    private final int columnDirection;

    Direction(final int rowDirection, final int columnDirection) {
        this.rowDirection = rowDirection;
        this.columnDirection = columnDirection;
    }

    public int getRowDirection() {
        return rowDirection;
    }

    public int getColumnDirection() {
        return columnDirection;
    }
}