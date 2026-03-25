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
        if (x < 0 || x > 8) {
            throw new IllegalArgumentException("x값은 0이상 8이하여야 합니다.");
        }
        if (y < 0 || y > 9) {
            throw new IllegalArgumentException("x값은 0이상 9이하여야 합니다.");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
