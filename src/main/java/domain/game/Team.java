package domain.game;

public enum Team {
    CHO(new Score(0.0)),
    HAN(new Score(Team.HAN_HANDICAP_SCORE));

    private static final double HAN_HANDICAP_SCORE = 1.5;
    private final Score initialScore;

    Team(Score initialScore) {
        this.initialScore = initialScore;
    }

    public Score getInitialScore() {
        return initialScore;
    }

    public int forwardRowDirection() {
        if (this == CHO) {
            return 1;
        }
        return -1;
    }
}
