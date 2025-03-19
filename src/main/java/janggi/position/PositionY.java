package janggi.position;

import java.util.Objects;

public class PositionY {

    private final int value;

    public PositionY(int value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Y 위치가 장기판을 벗어났습니다");
        }
    }

    public PositionY plus(int value) {
        return new PositionY(this.value + value);
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
