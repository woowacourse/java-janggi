package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public class Chariot extends AbstractPiece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Coordination from, Coordination to, Board board) {
        return false;
    }
}
