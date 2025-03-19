package janggi.position;

import java.util.Objects;

public class PositionX {

    private final int value;

    public PositionX(int value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("X 위치가 장기판을 벗어났습니다");
        }
    }

    public PositionX plus(int value) {
        return new PositionX(this.value + value);
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
