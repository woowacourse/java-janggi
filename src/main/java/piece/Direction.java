package piece;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    LEFT(0, -1),
    LEFT_TOP(1, -1),
    LEFT_BOTTOM(-1, -1),
    RIGHT(0, 1),
    RIGHT_TOP(1, 1),
    RIGHT_BOTTOM(-1, 1),
    TOP(1, 0),
    BOTTOM(-1, 0),
    ;

    private final int deltaRow;
    private final int deltaColumn;

    Direction(final int deltaRow, final int deltaColumn) {
        this.deltaRow = deltaRow;
        this.deltaColumn = deltaColumn;
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaColumn() {
        return deltaColumn;
    }

    public static List<Direction> getStraightDirection() {
        return Arrays.stream(values())
                .filter(direction -> direction.getDeltaRow() == 0 || direction.getDeltaColumn() == 0)
                .toList();
    }

    public static List<Direction> getCrossDirection() {
        return Arrays.stream(values())
                .filter(direction -> direction.getDeltaRow() != 0 && direction.getDeltaColumn() != 0)
                .toList();
    }

    public List<Direction> nextCrossDirection() {
        return getCrossDirection()
                .stream()
                .filter(this::isSameStraightDirection)
                .toList();
    }

    private boolean isSameStraightDirection(Direction direction) {
        if (deltaColumn != 0) {
            return direction.getDeltaColumn() == deltaColumn;
        }
        return direction.getDeltaRow() == deltaRow;
    }

    public boolean isCrossDirection() {
        return Math.abs(deltaColumn) + Math.abs(deltaRow) == 2;
    }

    public boolean isStraightDirection() {
        return Math.abs(deltaColumn) + Math.abs(deltaRow) == 1;
    }
}
