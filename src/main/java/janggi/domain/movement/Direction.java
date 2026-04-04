package janggi.domain.movement;

public final class Direction {

    public static final Direction UP = new Direction(-1, 0);
    public static final Direction DOWN = new Direction(1, 0);
    public static final Direction LEFT = new Direction(0, -1);
    public static final Direction RIGHT = new Direction(0, 1);
    public static final Direction UP_RIGHT = new Direction(-1, 1);
    public static final Direction UP_LEFT = new Direction(-1, -1);
    public static final Direction DOWN_RIGHT = new Direction(1, 1);
    public static final Direction DOWN_LEFT = new Direction(1, -1);
    private final int rowDirection;
    private final int columnDirection;

    private Direction(final int rowDirection, final int columnDirection) {
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
