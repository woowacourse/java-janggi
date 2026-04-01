package domain.coordinate;

public record Position(int col, int row) {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final int POSITION_THRESHOLD = 0;

    public Position nextPosition(Direction direction) {
        return new Position(col + direction.getCol(), row + direction.getRow());
    }

    public boolean isValidRange() {
        return col >= POSITION_THRESHOLD && col < COL_SIZE
                && row >= POSITION_THRESHOLD && row < ROW_SIZE;
    }

    public void validateRange() {
        if (col < POSITION_THRESHOLD || col >= COL_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 열 좌표: %d (열 좌표는 0 에서 9 사이여야 합니다.)", col));
        }

        if (row < POSITION_THRESHOLD || row >= ROW_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 행 좌표: %d (행 좌표는 0 에서 8 사이여야 합니다.)", row));
        }
    }
}
