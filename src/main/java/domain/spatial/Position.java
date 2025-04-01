package domain.spatial;

public record Position(
        int row,
        int column
) {

    // 장기 최대 - 최소 좌표
    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 9;
    public static final int MAX_COLUMN = 10;

    // 궁성 최대 - 최소 좌표
    public static final int PALACE_MIN_ROW = 4;
    public static final int PALACE_MAX_ROW = 6;
    public static final int PALACE_MAX_COLUMN_LEFT = 3;
    public static final int PALACE_MIN_COLUMN_RIGHT = 8;

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

    public boolean isWithinPalace() {
        boolean isWithinPalaceRow = this.row >= PALACE_MIN_ROW && this.row <= PALACE_MAX_ROW;
        boolean isWithinPalaceColumn = this.column <= PALACE_MAX_COLUMN_LEFT || this.column >= PALACE_MIN_COLUMN_RIGHT;
        return isWithinPalaceRow && isWithinPalaceColumn;
    }

    public Vector calculateVector(final Position target) {
        int moveRow = target.row - this.row;
        int moveColumn = target.column - this.column;

        return new Vector(moveRow, moveColumn);
    }

    private boolean isWithinRange(final int newRow, final int newColumn) {
        return newRow >= MIN_ROW && newRow <= MAX_ROW && newColumn >= MIN_COLUMN && newColumn <= MAX_COLUMN;
    }

    private void validateRange(final int row, final int column) {
        if (!isWithinRange(row, column)) {
            throw new IllegalArgumentException("좌표 입력은 9X10 보드 이내만 가능합니다.");
        }
    }
}
