package janggi.board;

public enum Movement {
    UP(0,1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    RIGHT_UP(RIGHT.x, UP.y),
    RIGHT_UP_UP(RIGHT.x, UP.y * 2),
    RIGHT_DOWN_DOWN(RIGHT.x, DOWN.y * 2),
    RIGHT_RIGHT_UP(RIGHT.x * 2, UP.y),
    RIGHT_RIGHT_DOWN(RIGHT.x * 2, DOWN.y),
    RIGHT_RIGHT_UP_UP_UP(RIGHT.x * 2, UP.y * 3),
    RIGHT_RIGHT_DOWN_DOWN_DOWN(RIGHT.x * 2, DOWN.y * 3),
    RIGHT_RIGHT_RIGHT_UP_UP(RIGHT.x * 3, UP.y * 2),
    RIGHT_RIGHT_RIGHT_DOWN_DOWN(RIGHT.x * 3, DOWN.y * 2),
    LEFT_UP(LEFT.x, UP.y),
    LEFT_UP_UP(LEFT.x, UP.y * 2),
    LEFT_DOWN_DOWN(LEFT.x, DOWN.y * 2),
    LEFT_LEFT_UP(LEFT.x * 2, UP.y),
    LEFT_LEFT_DOWN(LEFT.x * 2, DOWN.y),
    LEFT_LEFT_UP_UP_UP(LEFT.x * 2, UP.y * 3),
    LEFT_LEFT_DOWN_DOWN_DOWN(LEFT.x * 2, DOWN.y * 3),
    LEFT_LEFT_LEFT_UP_UP(LEFT.x * 3, UP.y * 2),
    LEFT_LEFT_LEFT_DOWN_DOWN(LEFT.x * 3, DOWN.y * 2);

    private final int x;
    private final int y;

    Movement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }


}
