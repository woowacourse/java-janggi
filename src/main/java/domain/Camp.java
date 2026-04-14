package domain;

public enum Camp {
    HAN(1.5),
    CHO(0),
    NONE(0);

    private final double bonusScore;

    Camp(double bonusScore) {
        this.bonusScore = bonusScore;
    }

    public Camp turnCamp() {
        if (this == HAN) {
            return CHO;
        }
        if (this == CHO) {
            return HAN;
        }
        throw new IllegalStateException("NONE 진영은 다음 턴이 없습니다.");
    }

    public double getBonusScore() {
        return this.bonusScore;
    }
}
