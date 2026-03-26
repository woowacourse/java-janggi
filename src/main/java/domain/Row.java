package domain;

public class Row {
    private final int value;

    public Row(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value <= 0 || value > 10) {
            throw new IllegalArgumentException("행의 위치는 1-10 사이에 있어야 합니다.");
        }
    }

    public int diff(Row other) {
        return this.value - other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Row row = (Row) o;
        return value == row.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
