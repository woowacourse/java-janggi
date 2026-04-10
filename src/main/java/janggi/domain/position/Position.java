package janggi.domain.position;

public record Position(
        int x,
        int y
) {
    private static final int MIN_X = 0;
    private static final int MAX_X = 8;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 9;

    public Position {
        validateCoordinate(x, y);
    }

    public static Position of(String rawX, String rawY) {
        return new Position(Integer.parseInt(rawX), Integer.parseInt(rawY));
    }

    public int deltaX(Position other) {
        return other.x - x;
    }

    public int deltaY(Position other) {
        return other.y - y;
    }

    public boolean isInsideCastle() {
        return Castle.isInsideCastle(this);
    }

    public boolean isInsideSameCastle(Position other) {
        return Castle.isSameCastle(this, other);
    }

    public boolean isDiagonalMoveInCastle(Position to) {
        if (!(this.isInsideCastle() && to.isInsideCastle())) {
            return false;
        }

        if (!this.isInsideSameCastle(to)) {
            return false;
        }

        return !(Castle.isCastleSide(this) || Castle.isCastleSide(to));
    }

    private void validateCoordinate(int x, int y) {
        if (x < MIN_X || x > MAX_X ||
                y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException("장기판을 벗어난 좌표입니다.");
        }
    }
}
