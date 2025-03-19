package janggi.position;

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
}
