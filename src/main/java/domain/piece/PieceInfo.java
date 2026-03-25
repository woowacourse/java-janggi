package domain.piece;

import domain.Country;

public class PieceInfo {
    private final PieceType pieceType;
    private final Country country;

    public PieceInfo(PieceType pieceType, Country country) {
        this.pieceType = pieceType;
        this.country = country;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Country getCountry() {
        return country;
    }
}
