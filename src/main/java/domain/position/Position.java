package domain.position;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import common.exception.JanggiException;

public record Position(int row, int column) {
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;
    private static final int TOP_PALACE_MIN_ROW = 0;
    private static final int TOP_PALACE_MAX_ROW = 2;
    private static final int BOTTOM_PALACE_MIN_ROW = 7;
    private static final int BOTTOM_PALACE_MAX_ROW = 9;

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

    public boolean isInPalace() {
        return isInTopPalace() || isInBottomPalace();
    }

    private boolean isInTopPalace() {
        return isInPalaceColumnRange() && isInPalaceRowRange(TOP_PALACE_MIN_ROW, TOP_PALACE_MAX_ROW);
    }

    private boolean isInBottomPalace() {
        return isInPalaceColumnRange() && isInPalaceRowRange(BOTTOM_PALACE_MIN_ROW, BOTTOM_PALACE_MAX_ROW);
    }

    private boolean isInPalaceRowRange(int minRow, int maxRow) {
        return row >= minRow && row <= maxRow;
    }

    private boolean isInPalaceColumnRange() {
        return column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN;
    }
}
