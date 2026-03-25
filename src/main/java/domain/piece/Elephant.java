package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public class Elephant extends AbstractPiece {

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Coordination from, Coordination to, Board board) {
        return false;
    }
}
