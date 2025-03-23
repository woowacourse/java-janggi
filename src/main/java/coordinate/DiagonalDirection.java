package coordinate;

public enum DiagonalDirection implements MoveVector {

    RIGHT_UP(1, -1),
    RIGHT_DOWN(1, 1),
    LEFT_UP(-1, -1),
    LEFT_DOWN(-1, 1),
    ;

    private final int deltaX;
    private final int deltaY;

    DiagonalDirection(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public int deltaX() {
        return deltaX;
    }

    @Override
    public int deltaY() {
        return deltaY;
    }
}
