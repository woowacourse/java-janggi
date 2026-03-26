package domain;

public class Column {
    private final int value;

    public Column(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value <= 0 || value > 9) {
            throw new IllegalArgumentException("열의 위치는 1-9 사이에 있어야 합니다.");
        }
    }

    public int diff(Column other) {
        return this.value - other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return value == column.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
