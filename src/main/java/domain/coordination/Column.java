package domain.coordination;

import java.util.ArrayList;
import java.util.List;

import static util.ErrorMessage.INVALID_COORDINATION;

public record Column(int index) {

    private static final int MIN = 1;
    private static final int MAX = 9;

    public Column {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (!(index >= MIN && index <= MAX)) {
            throw new IllegalArgumentException(INVALID_COORDINATION.getMessage());
        }
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
}
