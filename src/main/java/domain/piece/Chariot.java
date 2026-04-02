package domain.piece;

import domain.country.CountryType;

public class Chariot extends MoveStraightPiece {
    public Chariot(CountryType countryType) {
        super(new PieceInfo(PieceType.CHARIOT, countryType));
    }
}
