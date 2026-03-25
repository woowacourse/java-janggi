package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public class Horse extends AbstractPiece {

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Coordination from, Coordination to, Board board) {
        return false;
    }
}
