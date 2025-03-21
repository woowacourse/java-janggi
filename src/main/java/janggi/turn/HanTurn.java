package janggi.turn;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Team;

public class HanTurn extends Turn {

    private static final String HAN_NAME = "한";

    @Override
    public void changeTurn(Board board) {
        board.changeTurn(new ChoTurn());
    }

    @Override
    public String getTurnName() {
        return HAN_NAME;
    }

    @Override
    public boolean isMovingSameTeam(final Piece piece) {
        return piece.isSameTeam(Team.HAN);
    }
}
