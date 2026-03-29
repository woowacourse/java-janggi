package domain.position;

import static domain.common.Constant.MAX_COLUMN;
import static domain.common.Constant.MIN_COLUMN;

public record Column(int column) {

    public Column {
        validateColumn(column);

    }

    private void validateColumn(int column) {
        if (column > MAX_COLUMN || column < MIN_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }
}
