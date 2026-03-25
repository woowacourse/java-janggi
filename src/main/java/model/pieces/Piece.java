package model.pieces;

import model.Country;

import java.util.Objects;

public abstract class Piece {
    private final Country country;

    public Piece(Country country) {
        this.country = country;
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
}
