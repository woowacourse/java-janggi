package domain.player;

public final class Player {

    private final Team team;

    private Score score;
    private boolean isTurn;

    public Player(final Team team) {
        this.team = team;
        this.isTurn = this.team.isFirst();
        this.score = Score.generateInitialScoreByTeam(team);
    }

    public Team getTeam() {
        return team;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void switchTurn() {
        this.isTurn = !isTurn;
    }

    public void addScore(Score other) {
        this.score = score.add(other);
    }

    public Score getScore() {
        return score;
    }

}
