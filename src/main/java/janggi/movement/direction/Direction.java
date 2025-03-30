package janggi.movement.direction;

import janggi.game.Team;
import janggi.point.Point;
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
    REST(0, 0),
    ;

    private final int rowOffset;
    private final int columnOffset;

    Direction(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public static Direction toCardinalOrDiagonalFrom(Point startPoint, Point targetPoint) {
        Direction cardinalDirection = findCardinalDirection(startPoint, targetPoint);
        if (cardinalDirection != REST) {
            return cardinalDirection;
        }
        Direction diagonalDirection = findDiagonalDirection(startPoint, targetPoint);
        if (diagonalDirection == REST) {
            throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
        }
        return diagonalDirection;
    }

    public static List<Direction> toInitialCardinalThenDiagonalFrom(Point startPoint, Point targetPoint, int repeatCount
    ) {
        Direction firstDirection = findInitialCardinalDirection(startPoint, targetPoint);
        Direction repeatingDirection = findRepeatingDiagonalDirection(startPoint, targetPoint);
        return buildDirectionalPath(repeatCount, firstDirection, repeatingDirection);
    }

    private static Direction findInitialCardinalDirection(Point startPoint, Point targetPoint) {
        int rowGap = startPoint.row() - targetPoint.row();
        int columnGap = startPoint.column() - targetPoint.column();
        if (rowGap > columnGap) {
            if (startPoint.isRowBiggerThan(targetPoint)) {
                return NORTH;
            }
            if (startPoint.isRowLessThan(targetPoint)) {
                return SOUTH;
            }
        }
        if (rowGap < columnGap) {
            if (startPoint.isColumnBiggerThan(targetPoint)) {
                return WEST;
            }
            if (startPoint.isColumnLessThan(targetPoint)) {
                return EAST;
            }
        }
        throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
    }

    private static Direction findRepeatingDiagonalDirection(Point startPoint, Point targetPoint) {
        Direction diagonalDirection = findDiagonalDirection(startPoint, targetPoint);
        if (diagonalDirection == REST) {
            throw new IllegalArgumentException("이동이 불가능한 방향입니다.");
        }
        return diagonalDirection;
    }

    private static List<Direction> buildDirectionalPath(int repeatCount, Direction firstDirection,
                                                        Direction repeatingDirection) {
        List<Direction> directions = new ArrayList<>();
        directions.add(firstDirection);
        for (int i = 0; i < repeatCount; i++) {
            directions.add(repeatingDirection);
        }
        return directions;
    }

    private static Direction findDiagonalDirection(Point startPoint, Point targetPoint) {
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
        return REST;
    }

    private static Direction findCardinalDirection(Point startPoint, Point targetPoint) {
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
        return REST;
    }

    public boolean isBackwardDirectionOf(Team team) {
        return rowOffset == -team.getForwardDirection().rowOffset;
    }

    public boolean isDiagonal() {
        return rowOffset != 0 && columnOffset != 0;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColumnOffset() {
        return columnOffset;
    }
}
