package janggi.domain.piece;

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

    public boolean matchesForwardDirection(int direction) {
        return forwardDirection == direction;
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
