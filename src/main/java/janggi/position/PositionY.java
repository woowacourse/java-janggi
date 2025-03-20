package janggi.position;

import java.util.Objects;

public class PositionY {
    private final int value;

    public PositionY(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PositionY positionY = (PositionY) o;
        return value == positionY.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
