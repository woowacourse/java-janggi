package domain.coordination;

import static util.ErrorMessage.INVALID_COORDINATION;

public record Column(int index) {

    private static final int MIN = 1;
    private static final int MAX = 9;

    public Column {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(INVALID_COORDINATION.getMessage());
        }
    }

    public int different(Column column) {
        return this.index - column.index();
    }
}
