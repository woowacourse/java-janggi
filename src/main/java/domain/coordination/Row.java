package domain.coordination;

import util.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public record Row(int index) {

    private static final int MIN = 1;
    private static final int MAX = 10;

    public Row {
        validateRange(index);
    }

    public Row plus(int index) {
        return new Row(this.index + index);
    }

    public int different(Row row) {
        return this.index - row.index();
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COORDINATION.getMessage());
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
}
