package domain;

public abstract class PieceImpl implements Piece {
    private final Side side;

    public PieceImpl(Side side) {
        this.side = side;
    }

    @Override
    public final boolean isHan() {
        return side.isHan();
    }

    @Override
    public final boolean isCho() {
        return side.isCho();
    }

    @Override
    public final boolean isSameSide(Piece destinationPiece) {
        if (isHan() && destinationPiece.isHan()) {
            return true;
        }
        return isCho() && destinationPiece.isCho();
    }
}
