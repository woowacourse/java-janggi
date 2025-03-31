package janggi.domain.piece;

import janggi.domain.movement.Movement;

public record Position(int x, int y) {
    public Position {
        validatePosition(x, y);
    }

    public Position plus(final int x, final int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Movement getMovementTo(final Position other) {
        return new Movement(this.x - other.x, this.y - other.y);
    }

    private void validatePosition(final int x, final int y) {
        if (x <= 0 || x > 10 || y <= 0 || y > 9) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다");
        }
    }
}
