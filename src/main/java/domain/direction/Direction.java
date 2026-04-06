package domain.direction;

import domain.position.Position;

public enum Direction {

    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1),
    NORTH_EAST(-1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(1, -1);

    private final int offsetRow;
    private final int offsetColumn;

    Direction(int offsetRow, int offsetColumn) {
        this.offsetRow = offsetRow;
        this.offsetColumn = offsetColumn;
    }

    public static Direction fromStraight(int rowDifference, int columnDifference) {
        if (rowDifference < 0) {
            if (columnDifference < 0) {
                return NORTH_WEST;
            }
            if (columnDifference == 0) {
                return NORTH;
            }
            return NORTH_EAST;
        }
        if (rowDifference > 0) {
            if (columnDifference < 0) {
                return SOUTH_WEST;
            }
            if (columnDifference == 0) {
                return SOUTH;
            }
            return SOUTH_EAST;
        }
        if (columnDifference < 0) {
            return WEST;
        }
        if (columnDifference > 0) {
            return EAST;
        }
        throw new IllegalArgumentException("방향을 계산할 수 없습니다.");
    }

    public Position calculateNextPosition(Position source) {
        return new Position(source.row() + this.offsetRow, source.column() + this.offsetColumn);
    }

    public boolean canCalculateNextPosition(Position source) {
        int nextRow = source.row() + this.offsetRow;
        int nextColumn = source.column() + this.offsetColumn;
        return Position.isValid(nextRow, nextColumn);
    }

    public boolean isStraight() {
        return this == NORTH || this == SOUTH || this == EAST || this == WEST;
    }

    public boolean isDiagonal() {
        return this == NORTH_EAST || this == NORTH_WEST || this == SOUTH_EAST || this == SOUTH_WEST;
    }
}
