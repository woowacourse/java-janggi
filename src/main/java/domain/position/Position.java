package domain.position;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

public record Position(int row, int column) {
    public Position {
        validate(row, column);
    }

    private void validate(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("행값은 %s이상 %s이하여야 합니다.".formatted(MIN_ROW, MAX_ROW));
        }
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException("열값은 %s이상 %s이하여야 합니다.".formatted(MIN_COLUMN, MAX_COLUMN));
        }
    }
}
