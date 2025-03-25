package janggi.domain.piece.movement;

public record Movement(
        int x, int y
) {
    public static final Movement UP = new Movement(-1, 0);
    public static final Movement DOWN = new Movement(1, 0);
    public static final Movement LEFT = new Movement(0, -1);
    public static final Movement RIGHT = new Movement(0, 1);

    public Movement plus(Movement other) {
        return new Movement(x + other.x, y + other.y());
    }

    public static Movement findUnitMovement(int x, int y) {
        if (x < 0) {
            return UP;
        }
        if (x > 0) {
            return DOWN;
        }
        if (y < 0) {
            return LEFT;
        }
        if (y > 0) {
            return RIGHT;
        }
        throw new IllegalArgumentException("원래 위치로 이동할 수 없습니다.");
    }
}
