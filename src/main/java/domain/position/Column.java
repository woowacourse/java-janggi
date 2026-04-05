package domain.position;

import java.util.Objects;

public class Column {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 9;
    private static final String OUT_OF_RANGE = String.format("Column은 %d ~ %d 사이로 입력해야 합니다.", MIN_RANGE, MAX_RANGE);
    private final int value;

    public Column(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_RANGE || value > MAX_RANGE) {
            throw new IllegalArgumentException(OUT_OF_RANGE);
        }
    }

    public Column add(int value) {
        return new Column(this.value + value);
    }


    public Column add(Column value) {
        return new Column(this.value + value.getValue());
    }


    public Column getUpper(Column target) {
        if (value > target.value) {
            return this;
        }
        return target;
    }

    public Column getLower(Column target) {
        if (value < target.value) {
            return this;
        }
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return value == column.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }
}
