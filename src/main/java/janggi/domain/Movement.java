package janggi.domain;

import java.util.Arrays;

public enum Movement {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    UP_RIGHT(-1, 1),
    UP_LEFT(-1, -1),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(1, -1);

    private static final String INVALID_DELTA_DIRECTION_MESSAGE = "해당 dx,dy에 대한 movement가 없습니다.";

    private final int dx;
    private final int dy;

    Movement(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public static Movement of(int dx, int dy) {
        return Arrays.stream(Movement.values())
                .filter(movement ->  movement.dx == dx && movement.dy == dy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_DELTA_DIRECTION_MESSAGE));
    }
}
