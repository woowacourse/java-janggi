package domain;

public final class Player {
    private final Team team;
    private boolean turn;

    public Player(final Team team) {
        this.team = team;
        this.turn = team.isFirst();
    }

    public boolean isTurn() {
        return turn;
    }

    public void switchTurn() {
        turn = !turn;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isFirstAttack() {
        return team.isFirst();
    }
}
