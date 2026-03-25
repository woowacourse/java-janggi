package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public class Cannon extends AbstractPiece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Coordination from, Coordination to, Board board) {
        return false;
    }
}
