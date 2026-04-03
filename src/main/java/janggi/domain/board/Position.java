package janggi.domain.board;

public record Position(int row, int column) {

    private static final int MIN_POSITION_INDEX = 0;
    private static final int MAX_ROW_INDEX = 9;
    private static final int MAX_COLUMN_INDEX = 8;
    private static final String ROW_OUT_OF_RANGE = String.format(
            "[ERROR] 행은 %d행 이상 %d행 이하여야 합니다.",
            MIN_POSITION_INDEX,
            MAX_ROW_INDEX
    );
    private static final String COLUMN_OUT_OF_RANGE = String.format(
            "[ERROR] 열은 %d열 이상 %d열 이하여야 합니다.",
            MIN_POSITION_INDEX,
            MAX_COLUMN_INDEX
    );

    public Position {
        validateRow(row);
        validateColumn(column);
    }

    private void validateRow(int row) {
        if (row < MIN_POSITION_INDEX || MAX_ROW_INDEX < row) {
            throw new IllegalArgumentException(ROW_OUT_OF_RANGE);
        }
    }

    private void validateColumn(int column) {
        if (column < MIN_POSITION_INDEX || MAX_COLUMN_INDEX < column) {
            throw new IllegalArgumentException(COLUMN_OUT_OF_RANGE);
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
