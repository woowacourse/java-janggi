package janggi.domain.piece.movement;

public enum Movement {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP_LEFT(UP.x, LEFT.y),
    TOP_RIGHT(UP.x, RIGHT.y),
    BOTTOM_LEFT(DOWN.x, LEFT.y),
    BOTTOM_RIGHT(DOWN.x, RIGHT.y),
    TOP_LEFT_TOP_LEFT(UP.x * 2, LEFT.y * 2),
    TOP_RIGHT_TOP_RIGHT(UP.x * 2, RIGHT.y * 2),
    BOTTOM_LEFT_BOTTOM_LEFT(DOWN.x * 2, LEFT.y * 2),
    BOTTOM_RIGHT_BOTTOM_RIGHT(DOWN.x * 2, RIGHT.y * 2);

    private final int x;
    private final int y;

    Movement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Movement getDiagonal(int x, int y) {
        if(x > 0 && y < 0) {
            return BOTTOM_LEFT;
        }
        if(x > 0 && y > 0) {
            return BOTTOM_RIGHT;
        }
        if(x < 0 && y < 0) {
            return TOP_LEFT;
        }
        if(x < 0 && y > 0) {
            return TOP_RIGHT;
        }
        throw new IllegalArgumentException("대각선 이동이 아닙니다");
    }

    public static Movement getOrthogonal(int x, int y) {
        if(x == 0) {
            if(y < 0) {
                return LEFT;
            }
            if(y > 0) {
                return RIGHT;
            }
        }
        if(y == 0) {
            if(x > 0) {
                return DOWN;
            }
            if(x < 0) {
                return UP;
            }
        }
        throw new IllegalArgumentException("수직 이동이 아닙니다");
    }
}
