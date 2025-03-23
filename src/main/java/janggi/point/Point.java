package janggi.point;

public record Point(int row, int column) {

    private static final int BOARD_LOWER_BOUND = 0;
    private static final int BOARD_ROW_UPPER_BOUND = 9;
    private static final int BOARD_COLUMN_UPPER_BOUND = 8;

    public Point {
        validateRange(row, column);
    }

    private void validateRange(int row, int column) {
        if (row < BOARD_LOWER_BOUND
            || column < BOARD_LOWER_BOUND
            || row > BOARD_ROW_UPPER_BOUND
            || column > BOARD_COLUMN_UPPER_BOUND) {
            throw new IllegalArgumentException("보드판의 범위를 벗어난 좌표입니다.");
        }
    }

    public Point move(int rowMovingDistance, int columnMovingDistance) {
        return new Point(this.row + rowMovingDistance, this.column + columnMovingDistance);
    }

    public boolean isSameRow(Point targetPoint) {
        return this.row == targetPoint.row;
    }

    public boolean isSameColumn(Point targetPoint) {
        return this.column == targetPoint.column;
    }

    public boolean isColumnBiggerThan(Point targetPoint) {
        return this.column > targetPoint.column;
    }

    public boolean isColumnLessThan(Point targetPoint) {
        return this.column < targetPoint.column;
    }

    public boolean isRowBiggerThan(Point targetPoint) {
        return this.row > targetPoint.row;
    }

    public boolean isRowLessThan(Point targetPoint) {
        return this.row < targetPoint.row;
    }
}
