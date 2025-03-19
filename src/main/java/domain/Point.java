package domain;

public record Point(int row, int column) {

    public Point {
        validateRange(row, column);
    }

    public static Point of(int row, int column) {
        return new Point(row, column);
    }

    private void validateRange(int row, int column) {
        if (row < Board.START_ROW_INDEX || row > Board.END_ROW_INDEX) {
            throw new IllegalArgumentException(row + ": 행의 범위를 벗어난 값입니다.");
        }

        if (column < Board.START_COLUMN_INDEX || column > Board.END_COLUMN_INDEX) {
            throw new IllegalArgumentException(column + ": 열의 범위를 벗어난 값입니다.");
        }
    }
}
