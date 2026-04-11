package domain.position;

import static common.Constant.MAX_COLUMN;
import static common.Constant.MIN_COLUMN;

import java.util.Objects;

public class Column {

    private final int column;

    public Column(int column) {
        validateColumn(column);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Column objectToColumn)) {
            return false;
        }
        return column == objectToColumn.column;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(column);
    }

    private void validateColumn(int column) {
        if (column > MAX_COLUMN || column < MIN_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }
}
