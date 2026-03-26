package model.pieces;

import java.util.Objects;
import model.Country;

public abstract class Piece {
    private final Country country;
    private final PieceType pieceType;

    public Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public String mark() {
        return country().color() + pieceType.symbol() + Country.RESET;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return country == piece.country;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country);
    }

    public Country country() {
        return country;
    }
}
