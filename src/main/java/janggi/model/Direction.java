package janggi.model;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    LEFT(0, -1),
    LEFT_TOP(-1, -1),
    LEFT_BOTTOM(1, -1),
    RIGHT(0, 1),
    RIGHT_TOP(-1, 1),
    RIGHT_BOTTOM(1, 1),
    TOP(-1, 0),
    BOTTOM(1, 0),
    ;

    private final int deltaRow;
    private final int deltaColumn;

    Direction(final int deltaRow, final int deltaColumn) {
        this.deltaRow = deltaRow;
        this.deltaColumn = deltaColumn;
    }

    public int deltaRow() {
        return deltaRow;
    }

    public int deltaColumn() {
        return deltaColumn;
    }

    public boolean isCrossDirection() {
        return Math.abs(deltaRow) + Math.abs(deltaColumn) == 2;
    }

    public static List<Direction> getStraightDirection() {
        return Arrays.stream(values())
                .filter(direction -> direction.deltaRow() == 0 || direction.deltaColumn() == 0)
                .toList();
    }

    public static List<Direction> getCrossDirection() {
        return Arrays.stream(values())
                .filter(direction -> direction.deltaRow() != 0 && direction.deltaColumn() != 0)
                .toList();
    }

    public List<Direction> nextCrossDirection() {
        return getCrossDirection()
                .stream()
                .filter(this::isSameStraightDirection)
                .toList();
    }

    public static List<Direction> allDirections() {
        return Arrays.stream(values()).toList();
    }

    public static List<Direction> calculateBackDirection(Color color) {
        if (color == Color.BLUE) {
            return List.of(BOTTOM, LEFT_BOTTOM, RIGHT_BOTTOM);
        }
        return List.of(TOP, LEFT_TOP, RIGHT_TOP);
    }

    private boolean isSameStraightDirection(Direction direction) {
        if (deltaColumn != 0) {
            return direction.deltaColumn() == deltaColumn;
        }
        return direction.deltaRow() == deltaRow;
    }
}
