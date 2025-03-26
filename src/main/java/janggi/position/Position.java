package janggi.position;

public record Position(int row, int column) {

    private static final int ROW_MIN = 1;
    private static final int ROW_MAX = 10;
    private static final int COLUMN_MIN = 1;
    private static final int COLUMN_MAX = 9;

    public Position move(int rowMovement, int columnMovement) {
        return new Position(row + rowMovement, column + columnMovement);
    }

    public boolean isSameRow(Position position) {
        return position.row == row;
    }

    public boolean isSameColumn(Position position) {
        return position.column == column;
    }

    public boolean isOutOfBoards() {
        return  (row < ROW_MIN || row > ROW_MAX) || (column < COLUMN_MIN || column > COLUMN_MAX);
    }

    public boolean isVerticalFromPosition(Position position) {
        return isSameColumn(position);
    }

    public boolean isHorizontalFromPosition(Position position) {
        return isSameRow(position);
    }

    public int calculateRowDistance(Position position) {
        return Math.abs(position.row - row);
    }

    public int calculateColumnDistance(Position position) {
        return Math.abs(position.column - column);
    }
}
