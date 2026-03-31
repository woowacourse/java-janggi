package domain.coordination;

import java.util.ArrayList;
import java.util.List;


public record Column(int index) {

    private static final String ERROR_INVALID_COORDINATION = "좌표값이 잘못되었습니다.";

    private static final int MIN = 1;
    private static final int MAX = 9;

    public Column {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (index < MIN || index > MAX) {
            throw new IllegalArgumentException(ERROR_INVALID_COORDINATION);
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
}
