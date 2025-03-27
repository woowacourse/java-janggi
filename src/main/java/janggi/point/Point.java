package janggi.point;

public record Point(int row, int column) {

    private static final int LOWER_BOUND = 0;
    private static final int ROW_UPPER_BOUND = 9;
    private static final int COLUMN_UPPER_BOUND = 8;

    public Point {
        validateRange(row, column);
    }

    private void validateRange(int row, int column) {
        if (row < LOWER_BOUND
                || column < LOWER_BOUND
                || row > ROW_UPPER_BOUND
                || column > COLUMN_UPPER_BOUND
        ) {
            throw new IllegalArgumentException("보드판의 범위를 벗어난 좌표입니다.");
        }
    }

    public Point move(int rowOffset, int columnOffset) {
        return new Point(this.row + rowOffset, this.column + columnOffset);
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
