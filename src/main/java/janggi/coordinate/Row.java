package janggi.coordinate;

import java.util.ArrayList;
import java.util.List;

public record Row(int value) {

    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;

    public Row(final int value) {
        this.value = value;
        validateRange();
    }

    public static List<Row> defaults() {
        final List<Row> rows = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            rows.add(new Row(row));
        }
        return rows;
    }

    private void validateRange() {
        if (MIN_ROW <= value && value <= MAX_ROW) {
            return;
        }
        throw new IllegalArgumentException(String.format("%d ~ %d 의 행에만 접근할 수 있습니다", MIN_ROW, MAX_ROW));
    }

    public Row add(final int delta) {
        return new Row(this.value + delta);
    }

    public Distance distanceTo(final Row other) {
        return new Distance(Math.abs(this.value - other.value), 0);
    }

    public Vector vectorTo(final Row other) {
        return new Vector(other.value - this.value, 0);
    }
}
