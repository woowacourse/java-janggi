package janggi.move;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    LEFT_UP(-1, -1),
    RIGHT_UP(1, -1),
    LEFT_DOWN(-1, 1),
    RIGHT_DOWN(1, 1);

    final int deltaX;
    final int deltaY;

    Direction(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}