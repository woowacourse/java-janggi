package domain.board;

public record Point(int row, int column) {

    public static int START_ROW_INDEX = 1;
    public static int END_ROW_INDEX = 10;
    public static int START_COLUMN_INDEX = 1;
    public static int END_COLUMN_INDEX = 9;

    public Point {
        validateRange(row, column);
    }

    public static Point of(int row, int column) {
        return new Point(row, column);
    }

    private void validateRange(int row, int column) {
        if (row < START_ROW_INDEX || row > END_ROW_INDEX) {
            throw new IllegalArgumentException(row + " : [ERROR] 행의 범위를 벗어난 값입니다.");
        }

        if (column < START_COLUMN_INDEX || column > END_COLUMN_INDEX) {
            throw new IllegalArgumentException(column + " : [ERROR] 열의 범위를 벗어난 값입니다.");
        }
    }
}
