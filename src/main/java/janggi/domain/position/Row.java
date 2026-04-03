package janggi.domain.position;

import java.util.Objects;

public class Row {

    public static final int ROW_LOWER_THRESH_HOLD = 1;
    public static final int ROW_UPPER_THRESH_HOLD = 10;

    private final int value;

    public Row(int value) {
        validateRange(value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Row row = (Row) o;
        return value == row.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private void validateRange(int value) {
        if (value < ROW_LOWER_THRESH_HOLD || value > ROW_UPPER_THRESH_HOLD) {
            throw new IllegalArgumentException("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다");
        }
    }
}
