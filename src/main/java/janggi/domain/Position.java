package janggi.domain;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        validateRow(row);
        validateColumn(column);

        this.row = row;
        this.column = column;
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
}
