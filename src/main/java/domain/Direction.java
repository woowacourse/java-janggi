package domain;

import java.util.Optional;

public enum Direction {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1),
    NORTH_EAST(-1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(1, -1);

    private final int dRow;
    private final int dColumn;

    Direction(int dRow, int dColumn) {
        this.dRow = dRow;
        this.dColumn = dColumn;
    }

    public int dRow() {
        return dRow;
    }

    public int dColumn() {
        return dColumn;
    }

    public static Optional<Direction> of(Position from, Position to) {
        int deltaRow = to.row() - from.row();
        int deltaColumn = to.column() - from.column();
        return findWithDelta(deltaRow, deltaColumn);
    }

    private static Optional<Direction> findWithDelta(int deltaRow, int deltaColumn) {
        for (Direction direction : values()) {
            if (direction.hasDelta(deltaRow, deltaColumn)) {
                return Optional.of(direction);
            }
        }
        return Optional.empty();
    }

    private boolean hasDelta(int deltaRow, int deltaColumn) {
        return dRow == deltaRow && dColumn == deltaColumn;
    }
}