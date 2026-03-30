package janggi.domain;

public record Row(
        int value
) {
    private static final int MAX_SIZE = 9;

    public Row {
        validate(value);
    }

    private void validate(int value) {
        if (value < 0 || value > MAX_SIZE) {
            throw new IllegalArgumentException("행은 0 ~ 9 입니다.");
        }
    }
}
