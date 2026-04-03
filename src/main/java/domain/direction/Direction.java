package domain.direction;

import static common.exception.ErrorMessage.INVALID_DIRECTION;

import common.exception.JanggiException;
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
            return NORTH;
        }
        if (rowDifference > 0) {
            return SOUTH;
        }
        if (columnDifference < 0) {
            return WEST;
        }
        if (columnDifference > 0) {
            return EAST;
        }
        throw new JanggiException(INVALID_DIRECTION.formatted(rowDifference, columnDifference));
    }

    public Position calculateNextPosition(Position source) {
        return new Position(source.row() + this.offsetRow, source.column() + this.offsetColumn);
    }

    public boolean canCalculateNextPosition(Position source) {
        int nextRow = source.row() + this.offsetRow;
        int nextColumn = source.column() + this.offsetColumn;
        return Position.isValid(nextRow, nextColumn);
    }
}
