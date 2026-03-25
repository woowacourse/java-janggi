package domain.place.piece;

public class Horse extends Piece{

    public Horse(Side side) {
        super(side);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.HORSE;
    }
}
