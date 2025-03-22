package domain.board;

public record Point(int row, int column) {

    public Point {
        validateRange(row, column);
    }

    public static Point of(int row, int column) {
        return new Point(row, column);
    }

    public static boolean isInvalidRange(int row, int column) {
        return row < Board.START_ROW_INDEX || row > Board.END_ROW_INDEX
                || column < Board.START_COLUMN_INDEX || column > Board.END_COLUMN_INDEX;
    }

    private void validateRange(int row, int column) {
        if (isInvalidRange(row, column)) {
            throw new IllegalArgumentException(row + ", " + column + " : [ERROR] 행 또는 열의 범위를 벗어난 값입니다.");
        }
    }
}
