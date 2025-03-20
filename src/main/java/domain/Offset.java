package domain;

public record Offset(
        int x,
        int y
) {

    public Offset {
        validateRange(x, y);
    }

    private void validateRange(
            final int x,
            final int y
    ) {
        if (Math.abs(x) > 8 || Math.abs(y) > 9) {
            throw new IllegalArgumentException("오프셋의 범위를 벗어났습니다.");
        }
    }
}
