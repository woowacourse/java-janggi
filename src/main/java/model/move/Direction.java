package model.move;

import java.util.Arrays;

public enum Direction {
    NONE(0, 0),
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1);

    private final int rowOffset;
    private final int colOffset;

    Direction(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    public int moveRow(int x) {
        return x + this.rowOffset;
    }

    public int moveCol(int y) {
        return y + this.colOffset;
    }

    public static Direction from(int rowDiff, int colDiff) {
        int row = Integer.compare(rowDiff, 0);
        int col = Integer.compare(colDiff, 0);

        return Arrays.stream(values())
                .filter(dir -> (dir.rowOffset == row) && (dir.colOffset == col))
                .findFirst()
                .orElse(NONE);
    }
}
