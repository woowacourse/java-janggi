package model;

import java.util.Objects;
import model.pieces.rule.Direction;

public class Column {
    private final int value;
    private static final int MAXIMUM = 9;
    private static final int MINIMUM = 1;

    private Column(int value) {
        validate(value);
        this.value = value;
    }

    public static Column from(int value) {
        return new Column(value);
    }

    public Column move(Direction direction) {
        return Column.from(direction.moveCol(value));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return value == column.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    private void validate(int value) {
        if (value > MAXIMUM || value < MINIMUM) {
            throw new IllegalArgumentException("범위에 맞지 않는 숫자입니다.");
        }
    }
}
