package janggi.position;

import java.util.Objects;

public class PositionX {
    private final int value;

    public PositionX(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PositionX positionX = (PositionX) o;
        return value == positionX.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
