package domain.position;

public final class Row {
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

    public Row min(Row other) {
        return new Row(Math.min(other.value, value));
    }

    public Row max(Row other) {
        return new Row(Math.max(other.value, value));
    }

    public Row next() {
        return new Row(this.value + 1);
    }

    public boolean isLessThan(Row other) {
        return this.value < other.value;
    }

    public int diff(Row other) {
        return this.value - other.value;
    }

    public Row add(int measure) {
        return new Row(this.value + measure);
    }

    public Row divide(Row row) {
        return new Row((this.value + row.value) / 2);
    }

    public int getValue() {
        return value;
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
