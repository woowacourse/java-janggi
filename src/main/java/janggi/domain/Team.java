package janggi.domain;

public enum Team {

    HAN(1, 1.5),
    CHO(-1, 0),
    ;

    private final int direction;
    private final double bonusScore;

    Team(int direction, double bonusScore) {
        this.direction = direction;
        this.bonusScore = bonusScore;
    }

    public double getBonusScore() {
        return bonusScore;
    }
}
