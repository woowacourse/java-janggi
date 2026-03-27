package domain.position;

import static common.Constants.MAX_COL;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COL;
import static common.Constants.MIN_ROW;

public record Position(int row, int col) {
    public Position {
        validate(row, col);
    }

    private void validate(int row, int col) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("행값은 %s이상 %s이하여야 합니다.".formatted(MIN_ROW, MAX_ROW));
        }
        if (col < MIN_COL || col > MAX_COL) {
            throw new IllegalArgumentException("열값은 %s이상 %s이하여야 합니다.".formatted(MIN_COL, MAX_COL));
        }
    }
}
