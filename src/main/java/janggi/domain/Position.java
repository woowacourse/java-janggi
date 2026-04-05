package janggi.domain;

import janggi.exception.ExceptionMessage;
import java.util.List;

public record Position(int row, int column) {

    private static final int POSITION_SIZE = 2;
    private static final int MIN_POSITION_INDEX = 0;
    private static final int MAX_ROW_INDEX = 9;
    private static final int MAX_COLUMN_INDEX = 8;

    public static Position from(List<Integer> rawPosition) {
        validatePositionSize(rawPosition);
        return new Position(rawPosition.get(0), rawPosition.get(1));
    }

    private static void validatePositionSize(List<Integer> position) {
        if (position.size() != POSITION_SIZE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT_FORMAT.getMessage());
        }
    }

    public Position {
        validateRow(row);
        validateColumn(column);
    }

    private void validateRow(int row) {
        if (row < MIN_POSITION_INDEX || MAX_ROW_INDEX < row) {
            throw new IllegalArgumentException(ExceptionMessage.ROW_OUT_OF_RANGE.getMessage(MIN_POSITION_INDEX, MAX_ROW_INDEX));
        }
    }

    private void validateColumn(int column) {
        if (column < MIN_POSITION_INDEX || MAX_COLUMN_INDEX < column) {
            throw new IllegalArgumentException(ExceptionMessage.COLUMN_OUT_OF_RANGE.getMessage(MIN_POSITION_INDEX, MAX_COLUMN_INDEX));
        }
    }

    public int calculateRowDistance(Position other) {
        return row - other.row;
    }

    public int calculateColumnDistance(Position other) {
        return column - other.column;
    }

    public Position moveRow(int distance) {
        return new Position(row + distance, column);
    }

    public Position moveCol(int distance) {
        return new Position(row, column + distance);
    }

    public Position moveDiagonal(int rowDistance, int colDistance) {
        return new Position(row + rowDistance, column + colDistance);
    }
}
