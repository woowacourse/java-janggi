package coordinate;

public enum Direction implements MoveVector {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    ;

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
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
