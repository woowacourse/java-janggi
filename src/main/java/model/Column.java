package model;

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

    private void validate(int value) {
        if (value > MAXIMUM || value < MINIMUM) {
            throw new IllegalArgumentException("범위에 맞지 않는 숫자입니다.");
        }
    }
}
