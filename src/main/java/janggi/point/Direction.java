package janggi.point;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    NORTH(-1, 0),
    SOUTH(1, 0),
    WEST(0, -1),
    EAST(0, 1),
    NORTH_WEST(-1, -1),
    NORTH_EAST(-1, 1),
    SOUTH_WEST(1, -1),
    SOUTH_EAST(1, 1);

    private final int rowOffset;
    private final int columnOffset;

    Direction(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public static Direction cardinalFrom(Point startPoint, Point targetPoint) {
        if (startPoint.isSameRow(targetPoint)) {
            if (startPoint.isColumnBiggerThan(targetPoint)) {
                return WEST;
            }
            if (startPoint.isColumnLessThan(targetPoint)) {
                return EAST;
            }

        }
        if (startPoint.isSameColumn(targetPoint)) {
            if (startPoint.isRowBiggerThan(targetPoint)) {
                return NORTH;
            }
            if (startPoint.isRowLessThan(targetPoint)) {
                return SOUTH;
            }
        }
        throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
    }

    public static Direction cardinalOrDiagonalFrom(Point startPoint, Point targetPoint) {
        if (startPoint.isSameRow(targetPoint)) {
            if (startPoint.isColumnBiggerThan(targetPoint)) {
                return WEST;
            }
            if (startPoint.isColumnLessThan(targetPoint)) {
                return EAST;
            }

        }
        if (startPoint.isSameColumn(targetPoint)) {
            if (startPoint.isRowBiggerThan(targetPoint)) {
                return NORTH;
            }
            if (startPoint.isRowLessThan(targetPoint)) {
                return SOUTH;
            }
        }
        if (startPoint.isRowBiggerThan(targetPoint)) {
            if (startPoint.isColumnBiggerThan(targetPoint)) {
                return NORTH_WEST;
            }
            if (startPoint.isColumnLessThan(targetPoint)) {
                return NORTH_EAST;
            }
        }
        if (startPoint.isRowLessThan(targetPoint)) {
            if (startPoint.isColumnBiggerThan(targetPoint)) {
                return SOUTH_WEST;
            }
            if (startPoint.isColumnLessThan(targetPoint)) {
                return SOUTH_EAST;
            }
        }
        throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
    }

    public static List<Direction> oneCardinalAndDiagonalFrom(Point startPoint, Point targetPoint,
                                                             int maxBetweenRowAndColumnGap, int diagonalRepeatCount
    ) {
        int rowGap = startPoint.row() - targetPoint.row();
        int columnGap = startPoint.column() - targetPoint.column();
        if (rowGap == maxBetweenRowAndColumnGap) {
            if (columnGap > 0) {
                return doRouting(diagonalRepeatCount, NORTH, NORTH_WEST);
            }
            return doRouting(diagonalRepeatCount, NORTH, NORTH_EAST);
        }
        if (rowGap == -maxBetweenRowAndColumnGap) {
            if (columnGap > 0) {
                return doRouting(diagonalRepeatCount, SOUTH, SOUTH_WEST);
            }
            return doRouting(diagonalRepeatCount, SOUTH, SOUTH_EAST);
        }
        if (columnGap == maxBetweenRowAndColumnGap) {
            if (rowGap > 0) {
                return doRouting(diagonalRepeatCount, WEST, NORTH_WEST);
            }
            return doRouting(diagonalRepeatCount, WEST, SOUTH_WEST);
        }
        if (columnGap == -maxBetweenRowAndColumnGap) {
            if (rowGap > 0) {
                return doRouting(diagonalRepeatCount, EAST, NORTH_EAST);
            }
            return doRouting(diagonalRepeatCount, EAST, SOUTH_EAST);
        }
        throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
    }

    private static List<Direction> doRouting(int diagonalRepeatCount, Direction cardinal, Direction diagonal) {
        List<Direction> directions = new ArrayList<>();
        directions.add(cardinal);
        for (int i = 0; i < diagonalRepeatCount; i++) {
            directions.add(diagonal);
        }
        return directions;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColumnOffset() {
        return columnOffset;
    }
}
