package domain.board;

import domain.piece.Delta;

public record Position(
        int column,
        int row
) {

    private static final int MIN_COLUMN_RANGE = 1;
    private static final int MAX_COLUMN_RANGE = 10;
    private static final int MIN_ROW_RANGE = 1;
    private static final int MAX_ROW_RANGE = 9;

    public static Position of(final int column, final int row) {
        return new Position(column, row);
    }

    public Position move(final Position offset) {
        return Position.of(column + offset.column, row + offset.row);
    }

    public Position move(final Delta delta) {
        return Position.of(column + delta.column(), row + delta.row());
    }

    public boolean isInside() {
        return column >= MIN_COLUMN_RANGE && column <= MAX_COLUMN_RANGE
                && row >= MIN_ROW_RANGE && row <= MAX_ROW_RANGE;
    }

    public Position mirror() {
        int mirroredColumn = MAX_COLUMN_RANGE - column + 1;
        int mirroredRow = MAX_ROW_RANGE - row + 1;
        return Position.of(mirroredColumn, mirroredRow);
    }
}
