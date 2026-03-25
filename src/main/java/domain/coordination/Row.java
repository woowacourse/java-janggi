package domain.coordination;

import util.ErrorMessage;

public class Row {

    private static final int MIN = 1;
    private static final int MAX = 10;

    private final int index;

    public Row(int index) {
        validateRange(index);
        this.index = index;
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COORDINATION.getMessage());
        }
    }
}
