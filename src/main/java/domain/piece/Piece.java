package domain.piece;

import domain.Country;
import domain.PieceType;

public abstract class Piece {
    private Country country;
    private PieceType pieceType;

    protected Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public boolean isSameType(Piece piece) {
        return this.pieceType == piece.getPieceType();
    }

    public boolean isSameCountry(Piece piece) {
        return this.country == piece.getCountry();
    }

    public Country getCountry() {
        return country;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
