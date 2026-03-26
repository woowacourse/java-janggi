package janggi.domain;

public record Position(int row, int column) {

    public Position {
        validateRow(row);
        validateColumn(column);
    }

    private void validateRow(int row) {
        if (row < 0 || 9 < row) {
            throw new IllegalArgumentException("[ERROR] 행은 0행 이상 9행 이하여야 합니다.");
        }
    }

    private void validateColumn(int column) {
        if (column < 0 || 8 < column) {
            throw new IllegalArgumentException("[ERROR] 열은 0열 이상 8열 이하여야 합니다.");
        }
    }

    public int calculateRowDistance(Position other) {
        return row - other.row;
    }

    public int calculateColumnDistance(Position other) {
        return column - other.column;
    }

    public Position moveRow(int direction) {
        return new Position(row + direction, column);
    }

    public Position moveCol(int direction) {
        return new Position(row, column + direction);
    }

    public Position moveDiagonal(int rowDirection, int colDirection) {
        return new Position(row + rowDirection, column + colDirection);
    }
}
