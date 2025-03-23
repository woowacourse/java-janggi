package domain.board;

public record Point(int row, int column) {

    public static final int MIN_ROW_INDEX = 1;
    public static final int MAX_ROW_INDEX = 10;
    public static final int MIN_COLUMN_INDEX = 1;
    public static final int MAX_COLUMN_INDEX = 9;

    public Point {
        validateRange(row, column);
    }

    public static Point of(final int row, final int column) {
        return new Point(row, column);
    }

    private void validateRange(final int row, final int column) {
        if (row < MIN_ROW_INDEX || row > MAX_ROW_INDEX) {
            throw new IllegalArgumentException(row + " : [ERROR] 행의 범위를 벗어난 값입니다.");
        }

        if (column < MIN_COLUMN_INDEX || column > MAX_COLUMN_INDEX) {
            throw new IllegalArgumentException(column + " : [ERROR] 열의 범위를 벗어난 값입니다.");
        }
    }
}
