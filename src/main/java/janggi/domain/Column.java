package janggi.domain;

public record Column(
        int value
) {
    private static final int MAX_SIZE = 8;

    public Column {
        validate(value);
    }

    private void validate(int value) {
        if (value < 0 || value > MAX_SIZE) {
            throw new IllegalArgumentException("열은 0 ~ 8 입니다.");
        }
    }
}
