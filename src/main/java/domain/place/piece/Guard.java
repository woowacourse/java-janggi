package domain.place.piece;

public class Guard extends Piece {

    public Guard(Side side) {
        super(side);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.GUARD;
    }
}
