package domain.place.piece;

public class General extends Piece {

    public General(Side side) {
        super(side);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.GENERAL;
    }
}
