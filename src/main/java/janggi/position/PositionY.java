package janggi.position;

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
}
