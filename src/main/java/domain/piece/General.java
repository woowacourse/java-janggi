package domain.piece;

import domain.country.CountryType;

public class General extends MoveInsidePalacePiece {
    public General(CountryType countryType) {
        super(new PieceInfo(PieceType.GENERAL, countryType));
    }
}
