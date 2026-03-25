package domain.coordination;

import util.ErrorMessage;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return index == row.index;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }
}
