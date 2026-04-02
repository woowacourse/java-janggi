package domain.piece;

import domain.path.Direction;

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
