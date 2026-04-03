package domain.piece;

import domain.Direction;

public enum Team {
    CHO(Direction.DOWN), HAN(Direction.UP);

    private final Direction backwardDirection;

    Team(Direction backwardDirection) {
        this.backwardDirection = backwardDirection;
    }

    public Direction getBackwardDirection() {
        return backwardDirection;
    }
}
