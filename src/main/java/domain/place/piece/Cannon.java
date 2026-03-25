package domain.place.piece;

public class Cannon extends Piece {

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.CANNON;
    }
}
