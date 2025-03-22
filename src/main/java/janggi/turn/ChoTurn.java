package janggi.turn;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Team;

public class ChoTurn extends Turn {

    private static final String CHO_NAME = "초";

    @Override
    public Turn changeTurn(Board board) {
        return new HanTurn();
    }

    @Override
    public String getTurnName() {
        return CHO_NAME;
    }

    @Override
    public boolean isMovingSameTeam(final Piece piece) {
        return piece.isSameTeam(Team.CHO);
    }
}
