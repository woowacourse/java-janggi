package domain.position;

import domain.movement.Movement;

public record Position(int x, int y) {

    private static final int POSITION_MIN_RANGE = 0;
    private static final int X_MAX_RANGE = 9;
    private static final int Y_MAX_RANGE = 10;

    public Position {
        validateRange(x, y);
    }

    public double calculateDistance(final Position destPosition) {
        int dx = this.x - destPosition.x;
        int dy = this.y - destPosition.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void validateRange(int x, int y) {
        if (!isCoordinate(x, y)) {
            throw new IllegalArgumentException("존재할 수 없는 위치의 값입니다.");
        }
    }

    public boolean canMove(Movement movement) {
        int nextX = x + movement.x();
        int nextY = y + movement.y();
        return isCoordinate(nextX, nextY);
    }

    public static boolean isCoordinate(int x, int y) {
        return POSITION_MIN_RANGE < x && x <= X_MAX_RANGE && POSITION_MIN_RANGE < y && y <= Y_MAX_RANGE;
    }

    public Position move(Movement movement) {
        return new Position(x + movement.x(), y + movement.y());
    }

    public boolean isSameLine(final Position dest) {
        return isVertical(dest) || isHorizontal(dest);
    }

    public boolean isDiagonal(final Position dest) {
        int diffX = Math.abs(this.x - dest.x);
        int diffY = Math.abs(this.y - dest.y);
        return diffX == diffY;
    }

    private boolean isVertical(final Position dest) {
        return x == dest.x && y != dest.y;
    }

    private boolean isHorizontal(final Position dest) {
        return x != dest.x && y == dest.y;
    }

    public boolean isXGreaterThan(final Position dest) {
        return x >= (dest.x);
    }

    public boolean isXLessThan(final Position dest) {
        return x <= (dest.x);
    }
}
