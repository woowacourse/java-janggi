package domain.player;

public final class Player {

    private final Team team;
    private boolean isTurn;

    public Player(final TeamType type) {
        this.team = new Team(type);
        this.isTurn = team.isFirst();
    }

    public TeamType getTeam() {
        return team.getType();
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void switchTurn() {
        this.isTurn = !isTurn;
    }

    public void addScore(Score score) {
        team.addScore(score);
    }

    public Score getScore() {
        return team.getScore();
    }
}
