package domain.piece;

import domain.country.CountryType;

public class Guard extends MoveInsidePalacePiece {
    public Guard(CountryType countryType) {
        super(new PieceInfo(PieceType.GUARD, countryType));
    }
}
