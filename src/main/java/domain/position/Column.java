package domain.position;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MIN_COLUMN;

import java.util.Objects;

public record Column(int column) {

    public Column {
        validateColumn(column);

    }

    private void validateColumn(int column) {
        if (column > MAX_COLUMN || column < MIN_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
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
}
