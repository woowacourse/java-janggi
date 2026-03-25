package domain.position;

public record Position(
        int row,
        int column
) {
    public static Position of(int row, int column) {
        validateRange(row, column);
        return new Position(row, column);
    }

    private static void validateRange(int row, int column) {
        if (row < 0 || row > 10 || column < 0 || column > 9) {
            throw new IllegalArgumentException("장기판 범위를 벗어났습니다.");
        }
    }
}
