package janggi.turn;

import janggi.board.Board;
import janggi.piece.Team;

public abstract class Turn {

    public abstract Turn changeTurn(Board board);

    public abstract String getTurnName();

    public abstract boolean isMovingSameTeam(final Team team);
}
