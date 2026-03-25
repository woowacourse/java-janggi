package domain.place.piece;

public class Chariot extends Piece {

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.CHARIOT;
    }
}
