package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public class Guard extends AbstractPiece {

    public Guard(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Coordination from, Coordination to, Board board) {
        return false;
    }
}
