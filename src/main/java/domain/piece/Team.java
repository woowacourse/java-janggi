package domain.piece;

public enum Team {

    CHO(true, 0),
    HAN(false, 1.5),
    NONE(false, 0),
    ;

    private final boolean isCho;
    private final double bonusScore;

    Team(boolean isCho, double bonusScore) {
        this.isCho = isCho;
        this.bonusScore = bonusScore;
    }

    public boolean isCho() {
        return isCho;
    }

    public double bonusScore() {
        return bonusScore;
    }
}
