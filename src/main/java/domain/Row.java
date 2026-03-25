package domain;

public class Row {
    private final int value;

    public Row(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value <= 0 || value > 9) {
            throw new IllegalArgumentException("행의 위치는 1-9 사이에 있어야 합니다.");
        }
    }
}
