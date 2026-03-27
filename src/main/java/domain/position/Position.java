package domain.position;

import static common.Constants.MAX_COL;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COL;
import static common.Constants.MIN_ROW;

public record Position(int x, int y) {
    public Position {
        validate(x, y);
    }

    private void validate(int x, int y) {
        if (y < MIN_ROW || y > MAX_ROW) {
            throw new IllegalArgumentException("행값은 %s이상 %s이하여야 합니다.".formatted(MIN_ROW, MAX_ROW));
        }
        if (x < MIN_COL || x > MAX_COL) {
            throw new IllegalArgumentException("열값은 %s이상 %s이하여야 합니다.".formatted(MIN_COL, MAX_COL));
        }
    }
}
