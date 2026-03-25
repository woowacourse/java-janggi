package domain.coordination;

import util.ErrorMessage;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return index == column.index;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }
}
