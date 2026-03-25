package domain.coordination;

import util.ErrorMessage;

public class Column {

    private static final int MIN = 1;
    private static final int MAX = 9;

    private final int index;

    public Column(int index) {
        validateRange(index);
        this.index = index;
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COORDINATION.getMessage());
        }
    }
}
