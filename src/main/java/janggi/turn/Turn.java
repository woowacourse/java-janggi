package janggi.turn;

import janggi.piece.Team;

public abstract class Turn {

    protected final Team team;

    protected Turn(final Team team) {
        this.team = team;
    }

    public boolean isMovingSameTeam(final Team team) {
        return this.team == team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract Turn nextTurn();
}
