package janggi.domain.piece;

public record Position(int x, int y) {

    private static final int X_MIN_VALUE = 0;
    private static final int X_MAX_VALUE = 8;
    private static final int Y_MIN_VALUE = 0;
    private static final int Y_MAX_VALUE = 9;

    public Position {
        validate(x, y);
    }

    private void validate(int x, int y) {
        if (x < X_MIN_VALUE || x > X_MAX_VALUE) {
            throw new IllegalArgumentException("x의 범위가 잘못되었습니다.");
        }
        if (y < Y_MIN_VALUE || y > Y_MAX_VALUE) {
            throw new IllegalArgumentException("y의 범위가 잘못되었습니다.");
        }
    }

    public Position plus(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position plusY(int y) {
        return new Position(x, this.y + y);
    }

    public Position minusY(int y) {
        return new Position(x, this.y - y);
    }

    public boolean hasSameX(Position other) {
        return this.x == other.x;
    }

    public boolean hasSameY(Position other) {
        return this.y == other.y;
    }

    public int getXDistance(Position destination) {
        return Math.abs(destination.x - x);
    }

    public int getYDistance(Position destination) {
        return Math.abs(destination.y - y);
    }

    public boolean hasRelativeOffsetFrom(Position destination, int x, int y) {
        return getXDistance(destination) == x && getYDistance(destination) == y;
    }

    @Override
    public String toString() {
        return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position(int x1, int y1))) {
            return false;
        }
        return x == x1 && y == y1;
    }
}
