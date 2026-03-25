package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public class EmptyPiece implements Piece {

    public static final EmptyPiece INSTANCE = new EmptyPiece();


    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Team team() {
        return null;
    }

    @Override
    public boolean canMove(Coordination from, Coordination to, Board board) {
        return false;
    }
}
