package domain.board;

public record Position(int column, int row) {
    private static final int MIN_POSITION = 0;
    private static final int MAX_COLUMN = 8;
    private static final int MAX_ROW = 9;

    public Position {
        validateColumn(column);
        validateRow(row);

    }

    public Position move(int deltaX, int deltaY) {
        return new Position(this.column + deltaX, this.row + deltaY);
    }

    public int calculateDeltaX(Position destination) {
        return destination.column - this.column;
    }

    public int calculateDeltaY(Position destination) {
        return destination.row - this.row;
    }

    private void validateColumn(int column) {
        if (column < MIN_POSITION || column > MAX_COLUMN) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] x좌표는 %d에서 %d 사이입니다.", MIN_POSITION, MAX_COLUMN));
        }
    }

    private void validateRow(int row) {
        if (row < MIN_POSITION || row > MAX_ROW) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] y좌표는 %d에서 %d 사이입니다.", MIN_POSITION, MAX_ROW));
        }
    }
}
