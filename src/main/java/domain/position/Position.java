package domain.position;

public final class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        if (x <= 0 || x >= 10) {
            throw new IllegalArgumentException("x값은 1이상 9이하여야 합니다.");
        }
        if (y <= 0 || y >= 10) {
            throw new IllegalArgumentException("x값은 0이상 9이하여야 합니다.");
        }

    }
}
