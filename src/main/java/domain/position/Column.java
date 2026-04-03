package domain.position;

public class Column {

    private static final int MIN_COLUMN_NUMBER = 1;
    private static final int MAX_COLUMN_NUMBER = 9;

    private final int value;

    public Column(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_COLUMN_NUMBER || value > MAX_COLUMN_NUMBER) {
            throw new IllegalArgumentException("열의 위치는 1-9 사이에 있어야 합니다.");
        }
    }

    public Column divide(Column column) {
        return new Column((this.value + column.value) / 2);
    }

    public int min(Column other) {
        return Math.min(other.value, value);
    }

    public int max(Column other) {
        return Math.max(other.value, value);
    }

    public int diff(Column other) {
        return this.value - other.value;
    }

    public Column add(int measure) {
        return new Column(this.value + measure);
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
