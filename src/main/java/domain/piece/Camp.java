package domain.piece;

public enum Camp {
    CHO(-1, 0.0),
    HAN(1, 1.5);

    private final int forward;
    private final double bonusScore;

    Camp(int forward, double bonusScore) {
        this.forward = forward;
        this.bonusScore = bonusScore;
    }

    public int forward() {
        return this.forward;
    }

    public double bonusScore() {
        return this.bonusScore;
    }

    public Camp nextTurn() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
