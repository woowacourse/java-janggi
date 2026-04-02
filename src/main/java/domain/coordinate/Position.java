package domain.coordinate;

public record Position(int col, int row) {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final int POSITION_THRESHOLD = 0;

    public static Position of(int col, int row) {
        validateRange(col, row);
        return new Position(col, row);
    }

    public static Position unsafe(int col, int row) {
        return new Position(col, row);
    }

    public Position nextPosition(Direction direction) {
        return Position.unsafe(col + direction.getCol(), row + direction.getRow());
    }

    public boolean isValidRange() {
        return col >= POSITION_THRESHOLD && col < COL_SIZE
                && row >= POSITION_THRESHOLD && row < ROW_SIZE;
    }

    private static void validateRange(int col, int row) {
        if (col < POSITION_THRESHOLD || col >= COL_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 열 좌표: %d (열 좌표는 0 에서 9 사이여야 합니다.)", col));
        }

        if (row < POSITION_THRESHOLD || row >= ROW_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 행 좌표: %d (행 좌표는 0 에서 8 사이여야 합니다.)", row));
        }
    }
}
