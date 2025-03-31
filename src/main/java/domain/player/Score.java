package domain.player;

public record Score(Double value) {
    private static final Score INITIAL_SCORE_FOR_CHO = new Score(0.0);
    private static final Score INITIAL_SCORE_FOR_HAN = new Score(1.5);

    public static Score generateInitialScoreByTeam(final Team team) {
        if (team == Team.CHO) {
            return INITIAL_SCORE_FOR_CHO;
        }
        return INITIAL_SCORE_FOR_HAN;
    }

    public Score add(Score other) {
        return new Score(value + other.value);
    }
}
