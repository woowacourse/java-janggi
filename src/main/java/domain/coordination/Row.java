package domain.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Row {

    public static final int MIN = 1;
    public static final int MAX = 10;

    private static final String INVALID_COORDINATION_MESSAGE = "좌표값이 잘못되었습니다.";

    private final int index;

    public Row(int index) {
        validateRange(index);
        this.index = index;
    }

    public Row plus(int index) {
        return new Row(this.index + index);
    }

    public int different(Row row) {
        return this.index - row.index();
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(INVALID_COORDINATION_MESSAGE);
        }
    }

    public List<Row> between(Row row) {
        int min = Math.min(this.index, row.index);
        int max = Math.max(this.index, row.index);
        List<Row> rows = new ArrayList<>();
        for (int i = min + 1; i < max; i++) {
            rows.add(new Row(i));
        }
        return rows;
    }

    public int index() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Row) obj;
        return this.index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "Row[" +
                "index=" + index + ']';
    }

}
