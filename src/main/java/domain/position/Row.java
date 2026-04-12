package domain.position;

import java.util.Objects;

public class Row {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 10;
    private static final String OUT_OF_RANGE = String.format("Row는 %d ~ %d 사이로 입력해야 합니다.", MIN_RANGE, MAX_RANGE);
    private final int value;

    public Row(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_RANGE || value > MAX_RANGE) {
            throw new IllegalArgumentException(OUT_OF_RANGE);
        }
    }

    public Row add(int value) {
        return new Row(this.value + value);
    }

    public Row add(Row value) {
        return new Row(this.value + value.getValue());
    }

    public Row getUpper(Row row) {
        if (value > row.value) {
            return this;
        }
        return row;
    }

    public Row getLower(Row row) {
        if (value < row.value) {
            return this;
        }
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Row row = (Row) o;
        return value == row.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }

}
