package domain.position;

import java.util.Objects;

public class Row {
    private final int value;

    public Row(int value) {
        this.value = value;
    }

    public Row add(int value) {
        return new Row(this.value + value);
    }
    
    public int getValue() {
        return value;
    }

    public Row getUpper(Row row) {
        if (value > row.value) {
            return this;
        }
        return row;
    }

    public Row getLowerValue(Row row) {
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
}
