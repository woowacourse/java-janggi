package domain;

public record Offset(
    int x,
    int y
) {

    private static final int MAX_X_RANGE = 8;
    private static final int MAX_Y_RANGE = 9;

    public Offset {
        validateRange(x, y);
    }

    private void validateRange(
        final int x,
        final int y
    ) {
        if (Math.abs(x) > MAX_X_RANGE || Math.abs(y) > MAX_Y_RANGE) {
            throw new IllegalArgumentException("오프셋의 범위를 벗어났습니다.");
        }
    }
}
