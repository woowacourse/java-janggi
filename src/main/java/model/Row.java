package model;

public class Row {
    private final int value;
    private static final int MAXIMUM = 10;
    private static final int MINIMUM = 1;

    private Row(int value) {
        validate(value);
        this.value = value;
    }

    public static Row from(int value) {
        return new Row(value);
    }

    private void validate(int value) {
        if (value > MAXIMUM || value < MINIMUM) {
            throw new IllegalArgumentException("범위에 맞지 않는 숫자입니다.");
        }
    }
}
