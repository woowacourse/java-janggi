package domain.position;

import static domain.common.Constant.MAX_ROW;
import static domain.common.Constant.MIN_ROW;

import java.util.Objects;

public record Row(int row) {

    public Row {
        validateRange(row);
    }

    private void validateRange(int row) {
        if (row > MAX_ROW || row < MIN_ROW) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Row obejectToRow)) {
            return false;
        }
        return row == obejectToRow.row();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(row);
    }
}
