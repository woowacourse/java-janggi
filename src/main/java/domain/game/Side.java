package domain.game;

import domain.direction.Direction;
import domain.direction.Down;
import domain.direction.Left;
import domain.direction.Right;
import domain.direction.Up;

public enum Side {
    HAN(1, new Down(), new Right()),
    CHO(10, new Up(), new Left()),
    ;

    private final int baseRow;
    private final Direction forwardDirection;
    private final Direction backwardDirection;
    private final Direction leftDirection;
    private final Direction rightDirection;

    Side(
            int baseRow,
            Direction forwardDirection,
            Direction leftDirection
    ) {
        this.baseRow = baseRow;
        this.forwardDirection = forwardDirection;
        this.backwardDirection = forwardDirection.reverse();
        this.leftDirection = leftDirection;
        this.rightDirection = leftDirection.reverse();
    }

    public int getBaseRow() {
        return baseRow;
    }

    public Direction getForwardDirection() {
        return forwardDirection;
    }

    public Direction getBackwardDirection() {
        return backwardDirection;
    }

    public Direction getLeftDirection() {
        return leftDirection;
    }

    public Direction getRightDirection() {
        return rightDirection;
    }
}
