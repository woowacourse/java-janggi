package janggi.domain;

import janggi.exception.ExceptionMessage;

public record Position(int row, int column) {

    public static final int MIN_POSITION_INDEX = 0;
    public static final int MAX_ROW_INDEX = 9;
    public static final int MAX_COLUMN_INDEX = 8;

    public Position {
        validateRow(row);
        validateColumn(column);
    }

    private void validateRow(int row) {
        if (row < MIN_POSITION_INDEX || MAX_ROW_INDEX < row) {
            throw new IllegalArgumentException(ExceptionMessage.ROW_OUT_OF_RANGE.getMessage());
        }
    }

    private void validateColumn(int column) {
        if (column < MIN_POSITION_INDEX || MAX_COLUMN_INDEX < column) {
            throw new IllegalArgumentException(ExceptionMessage.COLUMN_OUT_OF_RANGE.getMessage());
        }
    }

    public int calculateRowDistance(Position other) {
        return row - other.row;
    }

    public int calculateColumnDistance(Position other) {
        return column - other.column;
    }

    public Position moveRow(int direction) {
        return new Position(row + direction, column);
    }

    public Position moveColumn(int direction) {
        return new Position(row, column + direction);
    }

    public Position moveDiagonal(int rowDirection, int columnDirection) {
        return new Position(row + rowDirection, column + columnDirection);
    }
}
