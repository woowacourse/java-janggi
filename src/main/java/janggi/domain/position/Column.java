package janggi.domain.position;

import java.util.Objects;

public class Column {

    public static final int COLUMN_LOWER_THRESH_HOLD = 1;
    public static final int COLUMN_UPPER_THRESH_HOLD = 9;

    private final int value;

    public Column(int value) {
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
        Column column = (Column) o;
        return value == column.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private void validateRange(int value) {
        if (value < COLUMN_LOWER_THRESH_HOLD || value > COLUMN_UPPER_THRESH_HOLD) {
            throw new IllegalArgumentException("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다");
        }
    }
}
