package janggi.turn;

import janggi.board.Board;
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
    public boolean isMovingSameTeam(final Team team) {
        return team == Team.CHO;
    }
}
