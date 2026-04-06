package domain;

public class Score {

    private final Double score;

    public Score(Double score) {
        this.score = score;
    }

    public static Score from(Team team, double value) {
        return new Score(value + team.getBonusScore());
    }

    public boolean isGreaterThan(Score other) {
        return this.score > other.score;
    }

}
