package domain.spatial;

public record Position(
        int row,
        int column
) {

    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 9;
    public static final int MAX_COLUMN = 10;

    public Position {
        validateRange(row, column);
    }

    public Position moveBy(final Vector vector) {
        return new Position(row + vector.moveRow(), column + vector.moveColumn());
    }

    public boolean isMoveValid(final Vector vector) {
        int newRow = row + vector.moveRow();
        int newColumn = column + vector.moveColumn();

        return isWithinRange(newRow, newColumn);
    }

    private void validateRange(final int row, final int column) {
        if (!isWithinRange(row, column)) {
            throw new IllegalArgumentException("좌표 입력은 9X10 보드 이내만 가능합니다.");
        }
    }

    private boolean isWithinRange(final int newRow, final int newColumn) {
        return newRow >= MIN_ROW && newRow <= MAX_ROW && newColumn >= MIN_COLUMN && newColumn <= MAX_COLUMN;
    }
}
