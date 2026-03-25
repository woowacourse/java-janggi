package domain.position;

public record Position(
        int row,
        int column
) {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_ROW = 1;
    private static final int MIN_COLUMN = 1;

    public static Position of(int row, int column) {
        validateRange(row, column);
        return new Position(row, column);
    }

    private static void validateRange(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW || column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException("장기판 범위를 벗어났습니다.");
        }
    }
}
