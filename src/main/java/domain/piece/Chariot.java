package domain.piece;

import domain.CountryType;

public class Chariot extends MoveStraightPiece {
    public Chariot(CountryType countryType) {
        super(new PieceInfo(PieceType.CHARIOT, countryType));
    }
}
