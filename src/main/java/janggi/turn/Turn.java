package janggi.turn;

import janggi.board.Board;
import janggi.piece.Piece;

public abstract class Turn {

    public abstract void changeTurn(Board board);

    public abstract String getTurnName();

    public abstract boolean isMovingSameTeam(final Piece piece);
}
