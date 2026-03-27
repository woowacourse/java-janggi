package domain.position;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;
import static common.exception.ErrorMessage.INVALID_COLUMN_RANGE;
import static common.exception.ErrorMessage.INVALID_ROW_RANGE;

public record Position(int row, int column) {
    public Position {
        validate(row, column);
    }

    private void validate(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException(INVALID_ROW_RANGE.formatted(MIN_ROW, MAX_ROW, row));
        }
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException(INVALID_COLUMN_RANGE.formatted(MIN_COLUMN, MAX_COLUMN, column));
        }
    }
}
