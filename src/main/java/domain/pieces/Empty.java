package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.PieceType;
import domain.Position;

public class Empty extends Piece {

    public Empty(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.NONE;
    }
}
