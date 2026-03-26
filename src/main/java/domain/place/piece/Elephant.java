package domain.place.piece;

public class Elephant extends Piece {

    public Elephant(Side side) {
        super(side);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.ELEPHANT;
    }
}
