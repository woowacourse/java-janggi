package domain.position;

import java.util.List;
import java.util.stream.IntStream;

public class Row {

    private static final int MIN_ROW_NUMBER = 1;
    private static final int MAX_ROW_NUMBER = 10;

    private final int value;

    public static List<Row> allRows() {
        return IntStream.rangeClosed(MIN_ROW_NUMBER, MAX_ROW_NUMBER)
                .mapToObj(Row::new)
                .toList();
    }

    public Row(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_ROW_NUMBER || value > MAX_ROW_NUMBER) {
            throw new IllegalArgumentException("행의 위치는 1-10 사이에 있어야 합니다.");
        }
    }

    public int value() {
        return value;
    }

    public int min(Row other) {
        return Math.min(other.value, value);
    }

    public int max(Row other) {
        return Math.max(other.value, value);
    }

    public int difference(Row other) {
        return this.value - other.value;
    }

    public Row add(int measure) {
        return new Row(this.value + measure);
    }

    public Row divide(Row row) {
        return new Row((this.value + row.value) / 2);
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
