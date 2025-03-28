package janggi.domain.piece;

import janggi.domain.Dynasty;

public abstract class Soldier extends Piece {

    public Soldier(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            return false;
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSameType(Piece piece) {
        return piece instanceof Soldier;
    }

    @Override
    public int score() {
        return 2;
    }
}
