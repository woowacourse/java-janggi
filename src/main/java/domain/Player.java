package domain;

public final class Player {

    private final Team team;
    private boolean isTurn;

    public Player(final Team team) {
        this.team = team;
        this.isTurn = team.isFirst();
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
}
