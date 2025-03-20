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
    SOUTH_EAST(1, 1),
    DEFAULT(0, 0);

    private final int rowDistance;
    private final int columnDistance;

    Direction(int rowDistance, int columnDistance) {
        this.rowDistance = rowDistance;
        this.columnDistance = columnDistance;
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
        return DEFAULT;
    }

    public static List<Direction> complexFrom(Point startPoint, Point targetPoint, int gap, int diagonalCount) {
        int rowGap = startPoint.row() - targetPoint.row();
        int columnGap = startPoint.column() - targetPoint.column();
        if (rowGap == gap) {
            if (columnGap > 0) {
                return doRouting(diagonalCount, NORTH, NORTH_WEST);
            }
            return doRouting(diagonalCount, NORTH, NORTH_EAST);
        }
        if (rowGap == -gap) {
            if (columnGap > 0) {
                return doRouting(diagonalCount, SOUTH, SOUTH_WEST);
            }
            return doRouting(diagonalCount, SOUTH, SOUTH_EAST);
        }
        if (columnGap == gap) {
            if (rowGap > 0) {
                return doRouting(diagonalCount, WEST, NORTH_WEST);
            }
            return doRouting(diagonalCount, WEST, SOUTH_WEST);
        }
        if (columnGap == -gap) {
            if (rowGap > 0) {
                return doRouting(diagonalCount, EAST, NORTH_EAST);
            }
            return doRouting(diagonalCount, EAST, SOUTH_EAST);
        }
        return List.of(DEFAULT);
    }

    private static List<Direction> doRouting(int diagonalCount, Direction cardinal, Direction diagonal) {
        List<Direction> directions = new ArrayList<>();
        directions.add(cardinal);
        for (int i = 0; i < diagonalCount; i++) {
            directions.add(diagonal);
        }
        return directions;
    }

    public Point move(Point point) {
        return point.move(rowDistance, columnDistance);
    }
}
