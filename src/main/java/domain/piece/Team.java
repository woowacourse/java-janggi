package domain.piece;

public enum Team {

    CHO(0),
    HAN(1.5);

    private final double bonusScore;

    Team(final double bonusScore) {
        this.bonusScore = bonusScore;
    }

    public double getBonusScore() {
        return this.bonusScore;
    }
}
