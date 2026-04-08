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
        this.column = column;
        this.row = row;
    }

    public static boolean isInsideBoundary(int column, int row) {
        return column >= MIN_COLUMN && column <= MAX_COLUMN && row >= MIN_ROW && row <= MAX_ROW;
    }

    public Position move(Direction direction) {
        return new Position(column + direction.getColumn(), row + direction.getRow());
    }

    public boolean cannotMoveTo(Direction direction) {
        int nextColumn = this.column + direction.getColumn();
        int nextRow = this.row + direction.getRow();
        return !isInsideBoundary(nextColumn, nextRow);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private void validateBoundary(int column, int row) {
        if (column < MIN_COLUMN || column > MAX_COLUMN || row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("[ERROR] 보드 범위를 벗어났습니다.");
        }
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
}
