package domain.piece;

public enum Camp {
    CHO(Direction.UP),
    HAN(Direction.DOWN);

    private final Direction forwardDirection;

    Camp(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    public Direction getForwardDirection() {
        return forwardDirection;
    }
}
