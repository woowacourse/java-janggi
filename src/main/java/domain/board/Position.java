package domain.board;

import domain.ErrorMessage;
import domain.Offset;

public record Position(int x, int y) {
    private static final int MIN_RANGE = 0;
    private static final int MAX_WIDTH_RANGE = 8;
    private static final int MAX_HEIGHT_RANGE = 9;

    private static final int LEFT_PALACE_COLUMN = 3;
    private static final int RIGHT_PALACE_COLUMN = 5;

    private static final int CHO_BOTTOM_PALACE_ROW = 0;
    private static final int CHO_TOP_PALACE_ROW = 2;

    private static final int HAN_BOTTOM_PALACE_ROW = 7;
    private static final int HAN_TOP_PALACE_ROW = 9;

    public Position {
        validateRange(x, y);
    }

    public boolean isInChoPalace() {
        return x >= LEFT_PALACE_COLUMN && x <= RIGHT_PALACE_COLUMN &&
                y >= CHO_BOTTOM_PALACE_ROW && y <= CHO_TOP_PALACE_ROW;
    }

    public boolean isInHanPalace() {
        return x >= LEFT_PALACE_COLUMN && x <= RIGHT_PALACE_COLUMN &&
                y >= HAN_BOTTOM_PALACE_ROW && y <= HAN_TOP_PALACE_ROW;
    }

    private void validateRange(int x, int y) {
        if (x < MIN_RANGE || x > MAX_WIDTH_RANGE) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_BOARD.getMessage());
        }

        if (y < MIN_RANGE || y > MAX_HEIGHT_RANGE) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_BOARD.getMessage());
        }
    }
}
