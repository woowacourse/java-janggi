package domain.position;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

import domain.Team;

public record Position(int row, int col) {
    private final static int PALACE_ROW_HAN = 2;
    private final static int PALACE_ROW_CHO = 7;
    private final static int PALACE_COL_MIN = 3;
    private final static int PALACE_COL_MAX = 5;


    public Position {
        validate();
    }

    public void validate() {
        if (isInvalid()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 좌표값입니다.");
        }
    }

    public Position next(int rowOffset, int colOffset) {
        return new Position(row + rowOffset, col + colOffset);
    }

    public boolean isInvalid() {
        return row < 0 || row >= BOARD_ROWS.getIndex() ||
                col < 0 || col >= BOARD_COLUMNS.getIndex();
    }

    public boolean isInPalace() {
        return (row <= PALACE_ROW_HAN || row >= PALACE_ROW_CHO) &&
                (col >= PALACE_COL_MIN && col <= PALACE_COL_MAX);
    }
}
