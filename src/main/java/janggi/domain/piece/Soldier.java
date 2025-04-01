package janggi.domain.piece;

public abstract class Soldier extends Piece {

    public Soldier(Dynasty dynasty) {
        super(dynasty);
    }

    public static Soldier from(Dynasty dynasty) {
        if (dynasty == Dynasty.HAN) {
            return new HanSoldier();
        }
        if (dynasty == Dynasty.CHU) {
            return new ChuSoldier();
        }
        throw new IllegalArgumentException("생성할 수 없는 dynasty입니다.");
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
    public PieceType pieceType() {
        return PieceType.SOLDIER;
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
