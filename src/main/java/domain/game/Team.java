package domain.game;

public enum Team {
    CHO(new Score(0.0)),
    HAN(new Score(Team.HAN_HANDICAP_SCORE));

    private static final double HAN_HANDICAP_SCORE = 1.5;
    private final Score initialScore;
    private Score teamScore;

    Team(Score initialScore) {
        this.initialScore = initialScore;
        this.teamScore = initialScore;
    }

    public Score getTeamScore() {
        return teamScore;
    }

    public int forwardRowDirection() {
        if (this == CHO) {
            return 1;
        }
        return -1;
    }

    public void addScore(Score score) {
        this.teamScore = this.teamScore.add(score);
    }

    public void resetScore() {
        this.teamScore = this.initialScore;
    }
}
