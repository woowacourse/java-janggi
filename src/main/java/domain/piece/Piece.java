package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public abstract class Piece {
    private final Country country;
    private final PieceType pieceType;

    protected Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public abstract void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to);

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
