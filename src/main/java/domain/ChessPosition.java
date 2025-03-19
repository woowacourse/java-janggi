package domain;

public record ChessPosition(
        int row,
        int column
) {
    private static final int MIN_ROW = 0;
    private static final int MAX_ROW = 9;
    private static final int MIN_COL = 0;
    private static final int MAX_COL = 8;

    public ChessPosition {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException("위치는 (0, 0) ~ (9, 8) 값만 가능합니다.");
        }
    }

    public static boolean isValid(final int row, final int col) {
        return row >= MIN_ROW && row <= MAX_ROW && col >= MIN_COL && col <= MAX_COL;
    }
}
