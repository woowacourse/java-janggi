package janggi.turn;

import janggi.piece.Team;

public abstract class Turn {

    public abstract Turn nextTurn();

    public abstract String getTurnName();

    public abstract boolean isMovingSameTeam(final Team team);
}
