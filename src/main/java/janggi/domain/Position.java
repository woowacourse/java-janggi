package janggi.domain;

import java.util.Objects;

public class Position {

    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;

    private final int row;
    private final int column;

    public Position(int column, int row) {
        validateBoundary(column, row);
        this.row = column;
        this.column = row;
    }

    public static boolean isInsideBoundary(int column, int row) {
        return column >= MIN_COLUMN && column <= MAX_COLUMN && row >= MIN_ROW && row <= MAX_ROW;
    }

    public Position move(Direction direction) {
        return new Position(row + direction.getColumn(), column + direction.getRow());
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) {
            return false;
        }
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    private void validateBoundary(int column, int row) {
        if (column < MIN_COLUMN || column > MAX_COLUMN || row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("[ERROR] 보드 범위를 벗어났습니다.");
        }
    }
}
