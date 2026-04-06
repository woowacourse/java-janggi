package domain.position;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

public record Position(int row, int col) {

    public Position {
        validate();
    }

    public void validate() {
        if (isInvalid()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 좌표값입니다.");
        }
    }

    public boolean isInvalid() {
        return row < 0 || row >= BOARD_ROWS.getIndex() ||
                col < 0 || col >= BOARD_COLUMNS.getIndex();
    }
}
