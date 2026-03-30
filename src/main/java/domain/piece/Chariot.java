package domain.piece;

import domain.Country;

public class Chariot extends MoveStraightPiece {
    public Chariot(Country country) {
        super(new PieceInfo(PieceType.CHARIOT, country));
    }
}
