package domain;

public class Column {
    private final int value;

    public Column(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value <= 0 || value > 10) {
            throw new IllegalArgumentException("열의 위치는 1-10 사이에 있어야 합니다.");
        }
    }

}
