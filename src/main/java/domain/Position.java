package domain;

public record Position(
    int x,
    int y
) {

    public Position {
        validateRange(x, y);
    }

    private void validateRange(
        final int x,
        final int y
    ) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            throw new IllegalArgumentException("장기판의 범위를 벗어났습니다.");
        }
    }
}
