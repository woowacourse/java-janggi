package janggi.domain.piece;

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

    @Override
    protected boolean isKing() {
        return false;
    }
}
