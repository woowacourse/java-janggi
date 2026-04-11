package domain.direction;

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
        throw new JanggiException("갈 수 있는 경로가 없습니다. row 차이: %s, column 차이: %s"
                .formatted(rowDifference, columnDifference));
    }

    public static Direction fromDiagonal(int rowDifference, int columnDifference) {
        int absoluteRowDifference = Math.abs(rowDifference);
        int absoluteColumnDifference = Math.abs(columnDifference);

        if (absoluteRowDifference != absoluteColumnDifference) {
            throw new JanggiException("이동할 수 없습니다.");
        }

        if (rowDifference < 0 && columnDifference > 0) {
            return NORTH_EAST;
        }
        if (rowDifference < 0) {
            return NORTH_WEST;
        }
        if (columnDifference > 0) {
            return SOUTH_EAST;
        }
        return SOUTH_WEST;
    }

    public Position calculateNextPosition(Position source) {
        return new Position(source.row() + this.offsetRow, source.column() + this.offsetColumn);
    }
}
