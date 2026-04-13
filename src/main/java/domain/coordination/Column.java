package domain.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Column {

    public static final int MIN = 1;
    public static final int MAX = 9;

    private static final String INVALID_COORDINATION_MESSAGE = "좌표값이 잘못되었습니다.";

    private final int index;

    public Column(int index) {
        validateRange(index);
        this.index = index;
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(INVALID_COORDINATION_MESSAGE);
        }
    }

    public Column plus(int index) {
        return new Column(this.index + index);
    }

    public int different(Column column) {
        return this.index - column.index();
    }

    public List<Column> between(Column column) {
        int min = Math.min(this.index, column.index);
        int max = Math.max(this.index, column.index);
        List<Column> columns = new ArrayList<>();
        for (int i = min + 1; i < max; i++) {
            columns.add(new Column(i));
        }
        return columns;
    }

    public int index() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Column) obj;
        return this.index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "Column[" +
                "index=" + index + ']';
    }
}
