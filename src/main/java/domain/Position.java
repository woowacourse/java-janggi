package domain;

public class Position {
    private static final int MIN_POSITION = 0;
    private static final int MAX_COLUMN = 8;
    private static final int MAX_ROW = 9;

    private final int column;
    private final int row;

    public Position(int column, int row) {
        validateColumn(column);
        validateRow(row);

        this.column = column;
        this.row = row;
    }

    private void validateColumn(int column) {
        if (column < 0 || column > MAX_COLUMN) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] x좌표는 %d에서 %d 사이입니다.", MIN_POSITION, MAX_COLUMN));
        }
    }

    private void validateRow(int row) {
        if (row < 0 || row > MAX_ROW) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] y좌표는 %d에서 %d 사이입니다.", MIN_POSITION, MAX_ROW));
        }
    }
}
