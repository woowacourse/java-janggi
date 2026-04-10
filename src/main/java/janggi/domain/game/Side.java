package janggi.domain.game;

import janggi.domain.board.Direction;

public enum Side {
    CHO(Direction.N),
    HAN(Direction.S),
    ;

    private final Direction forwardDirection;

    Side(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    public Side opposite() {
        if (this.equals(CHO)) {
            return HAN;
        }
        return CHO;
    }

    public Direction forwardDirection() {
        return forwardDirection;
    }

    public boolean hasAdvantage() {
        return this.equals(CHO);
    }
}
