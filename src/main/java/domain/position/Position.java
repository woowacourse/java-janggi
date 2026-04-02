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

    private static final Position TOP_LEFT = new Position(0, 3);
    private static final Position TOP_CENTER = new Position(1, 4);
    private static final Position TOP_RIGHT = new Position(0, 5);
    private static final Position TOP_BOTTOM_LEFT = new Position(2, 3);
    private static final Position TOP_BOTTOM_RIGHT = new Position(2, 5);

    private static final Position BOTTOM_TOP_LEFT = new Position(7, 3);
    private static final Position BOTTOM_CENTER = new Position(8, 4);
    private static final Position BOTTOM_TOP_RIGHT = new Position(7, 5);
    private static final Position BOTTOM_BOTTOM_LEFT = new Position(9, 3);
    private static final Position BOTTOM_BOTTOM_RIGHT = new Position(9, 5);

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

    public boolean isPalaceDiagonalReachable(Position other) {
        if (!isInPalace() || !other.isInPalace()) {
            return false;
        }

        return isOnSamePalaceDiagonalLine(other, TOP_LEFT, TOP_CENTER, TOP_BOTTOM_RIGHT)
                || isOnSamePalaceDiagonalLine(other, TOP_RIGHT, TOP_CENTER, TOP_BOTTOM_LEFT)
                || isOnSamePalaceDiagonalLine(other, BOTTOM_TOP_LEFT, BOTTOM_CENTER, BOTTOM_BOTTOM_RIGHT)
                || isOnSamePalaceDiagonalLine(other, BOTTOM_TOP_RIGHT, BOTTOM_CENTER, BOTTOM_BOTTOM_LEFT);
    }

    private boolean isOnSamePalaceDiagonalLine(Position other, Position first, Position middle, Position last) {
        return isInLinePair(this, other, first, middle)
                || isInLinePair(this, other, middle, last)
                || isInLinePair(this, other, first, last);
    }

    private boolean isInLinePair(Position source, Position destination, Position start, Position end) {
        return (source.equals(start) && destination.equals(end))
                || (source.equals(end) && destination.equals(start));
    }
}
