package domain.position;

import static common.Constant.MAX_ROW;
import static common.Constant.MIN_ROW;

import java.util.Objects;

public class Row {

    private final int row;

    public Row(int row) {
        validateRange(row);
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Row obejectToRow)) {
            return false;
        }
        return row == obejectToRow.row;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(row);
    }

    private void validateRange(int row) {
        if (row > MAX_ROW || row < MIN_ROW) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }
}
