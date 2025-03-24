package janggi.movement.direction;

import janggi.point.Point;
import java.util.ArrayList;
import java.util.Arrays;
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
    ;

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

    public static List<Direction> oneCardinalAndRepeatingDiagonalFrom(Point startPoint, Point targetPoint, int repeatCount
    ) {
        Direction firstDirection = findFirstDirection(startPoint, targetPoint);
        Direction repeatingDirection = findSecondDirection(startPoint, targetPoint);
        return doRouting(repeatCount, firstDirection, repeatingDirection);
    }

    private static Direction findFirstDirection(Point startPoint, Point targetPoint) {
        int rowGap = startPoint.row() - targetPoint.row();
        int columnGap = startPoint.column() - targetPoint.column();
        if (rowGap > columnGap) {
            if (startPoint.isRowBiggerThan(targetPoint)) {
                return NORTH;
            }
            if (startPoint.isRowLessThan(targetPoint)) {
                return  SOUTH;
            }
        }
        if (rowGap < columnGap) {
            if (startPoint.isColumnBiggerThan(targetPoint)) {
                return  WEST;
            }
            if (startPoint.isColumnLessThan(targetPoint)) {
                return EAST;
            }
        }
        throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
    }

    private static Direction findSecondDirection(Point startPoint, Point targetPoint) {
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

    private static List<Direction> doRouting(int repeatCount, Direction firstDirection, Direction repeatingDirection) {
        List<Direction> directions = new ArrayList<>();
        directions.add(firstDirection);
        for (int i = 0; i < repeatCount; i++) {
            directions.add(repeatingDirection);
        }
        return directions;
    }

    public Direction reverse() {
        return Arrays.stream(Direction.values())
                .filter(direction ->
                        direction.rowOffset == -this.rowOffset
                        && direction.columnOffset == -this.columnOffset)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColumnOffset() {
        return columnOffset;
    }
}
