package domain.position;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import common.exception.JanggiException;

public record Position(int row, int column) {
    public Position {
        validate(row, column);
    }

    private void validate(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new JanggiException("행값은 %s이상 %s이하여야 합니다. 입력값: %s".formatted(MIN_ROW, MAX_ROW, row));
        }
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new JanggiException(
                    "열값은 %s이상 %s이하여야 합니다. 입력값: %s".formatted(MIN_COLUMN, MAX_COLUMN, column)
            );
        }
    }
}
