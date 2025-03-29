package janggi.domain.movestep;

public enum MoveStep {

    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    LEFT_UP(-1, -1),
    RIGHT_UP(1, -1),
    LEFT_DOWN(-1, 1),
    RIGHT_DOWN(1, 1);

    private final int deltaX;
    private final int deltaY;

    MoveStep(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public boolean isUpDirection() {
        return deltaY <= 0;
    }

    public boolean isDownDirection() {
        return deltaY >= 0;
    }

    public int deltaX() {
        return deltaX;
    }

    public int deltaY() {
        return deltaY;
    }
}
