package domain.direction;

import java.util.Arrays;

public enum Direction {

    UP(1, 0, true),
    UP_RIGHT(1, 1, false),
    RIGHT(0, 1, true),
    DOWN_RIGHT(-1, 1, false),
    DOWN(-1, 0, true),
    DOWN_LEFT(-1, -1, false),
    LEFT(0, -1, true),
    UP_LEFT(1, -1, false);

    private final int dRow;
    private final int dColumn;
    private final boolean isStraight;

    Direction(int dRow, int dColumn, boolean isStraight) {
        this.dRow = dRow;
        this.dColumn = dColumn;
        this.isStraight = isStraight;
    }

    public static Direction from(int row, int col) {
        return Arrays.stream(values())
                .filter(direction -> direction.dRow == row && direction.dColumn == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당되는 단위 방향이 아닙니다."));
    }

    public boolean isStraight() {
        return isStraight;
    }

    public boolean isDiagonal() {
        return !isStraight;
    }

    public int getdRow() {
        return dRow;
    }

    public int getdColumn() {
        return dColumn;
    }

    public boolean isSameAtLeastOne(Direction direction) {
        return isDiagonal() && (dRow == direction.dRow || dColumn == direction.dColumn);
    }

    public boolean isSameDirection(Direction direction) {
        return this == direction;
    }
}
