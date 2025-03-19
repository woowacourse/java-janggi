package domain;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    LEFT(-1, 0),
    LEFT_TOP(-1, 1),
    LEFT_BOTTOM(-1, -1),
    RIGHT(1, 0),
    RIGHT_TOP(1, 1),
    RIGHT_BOTTOM(1, -1),
    TOP(0, 1),
    BOTTOM(0, -1),
    ;

    private final int deltaRow;
    private final int deltaColumn;

    Direction(int deltaRow, int deltaColumn) {
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
        if (deltaColumn != 0) {
            return Arrays.stream(Direction.values())
                    .filter(crossDirection -> crossDirection.getDeltaColumn() == deltaColumn)
                    .toList();
        }
        return Arrays.stream(Direction.values())
                .filter(crossDirection -> crossDirection.getDeltaRow() == deltaRow)
                .toList();
    }
}
