package janggi.domain.piece.camp;

public enum CampType {

    HAN(-1, 9, 73.5),
    CHO(1, 0, 72.0),
    ;

    private final int forwardDirection;
    private final int startRowPosition;
    private final double startScore;

    CampType(int forwardDirection, int startRowPosition, double startScore) {
        this.forwardDirection = forwardDirection;
        this.startRowPosition = startRowPosition;
        this.startScore = startScore;
    }

    public boolean matchesForwardDirection(int direction) {
        return forwardDirection == direction;
    }

    public int getStartRowPosition() {
        return startRowPosition;
    }

    public CampType next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public double getStartScore() {
        return startScore;
    }
}
