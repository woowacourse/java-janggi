package janggi.value;

public enum Direction {
    ORIGIN(new RelativePosition(0, 0)),
    LEFT(new RelativePosition(-1, 0)),
    RIGHT(new RelativePosition(1, 0)),
    UP(new RelativePosition(0, -1)),
    DOWN(new RelativePosition(0, 1)),
    UP_LEFT(new RelativePosition(-1, -1)),
    DOWN_LEFT(new RelativePosition(-1, 1)),
    UP_RIGHT(new RelativePosition(1, -1)),
    DOWN_RIGHT(new RelativePosition(1, 1));

    private final RelativePosition relativePosition;

    Direction(RelativePosition relativePosition) {
        this.relativePosition = relativePosition;
    }

    public RelativePosition getRelativePosition() {
        return relativePosition;
    }
}
