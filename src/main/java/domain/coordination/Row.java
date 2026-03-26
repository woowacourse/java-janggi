package domain.coordination;

import util.ErrorMessage;

public record Row(int index) {

    private static final int MIN = 1;
    private static final int MAX = 10;

    public Row {
        validateRange(index);
    }

    public int different(Row row) {
        return this.index - row.index();
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COORDINATION.getMessage());
        }
    }
}
