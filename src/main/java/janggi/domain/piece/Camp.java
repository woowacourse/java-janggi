package janggi.domain.piece;

import janggi.exception.ExceptionMessage;

public enum Camp {

    HAN(-1, 9),
    CHO(1, 0),
    ;

    private final int forwardDirection;
    private final int startRowPosition;

    Camp(int forwardDirection, int startRowPosition) {
        this.forwardDirection = forwardDirection;
        this.startRowPosition = startRowPosition;
    }

    public void validateForwardDirection(int rowDirection) {
        if (forwardDirection != rowDirection && rowDirection != 0) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }

    public int getStartRowPosition() {
        return startRowPosition;
    }

    public Camp next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
