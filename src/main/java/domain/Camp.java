package domain;

public enum Camp {
    HAN("한나라", 1.5),
    CHO("초나라", 0),
    NONE("", 0);

    private final String displayName;
    private final double bonusScore;

    Camp(String displayName, double bonusScore) {
        this.displayName = displayName;
        this.bonusScore = bonusScore;
    }

    public String getDisplayName() {
        return displayName;
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
