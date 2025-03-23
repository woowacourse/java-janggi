package janggi.coordinate;

public record Column(int value) {

    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 9;

    public Column(final int value) {
        this.value = value;
        validateRange();
    }

    private void validateRange() {
        if (MIN_COLUMN <= value && value <= MAX_COLUMN) {
            return;
        }
        throw new IllegalArgumentException(String.format("%d ~ %d 의 열에만 접근할 수 있습니다", MIN_COLUMN, MAX_COLUMN));
    }

    public Column add(final int delta) {
        return new Column(this.value + delta);
    }

    public Distance distanceTo(Column other) {
        return new Distance(0, Math.abs(this.value - other.value));
    }

    public Vector vectorTo(Column other) {
        return new Vector(0, other.value - this.value);
    }
}
