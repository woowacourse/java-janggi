package domain.player;

public final class Team {

    private final TeamType teamType;
    private Score score;

    public Team(final TeamType teamType) {
        this.teamType = teamType;
        this.score = Score.generateInitialScoreByTeam(teamType);
    }

    public TeamType getType() {
        return teamType;
    }

    public boolean isFirst() {
        return teamType.isFirst();
    }

    public Score getScore() {
        return score;
    }

    public void addScore(Score other) {
        this.score = score.add(other);
    }
}
