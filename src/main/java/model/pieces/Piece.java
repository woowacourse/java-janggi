package model.pieces;

import java.util.Objects;
import model.Country;

public abstract class Piece {
    private final Country country;

    public Piece(Country country) {
        this.country = country;
    }

    abstract public String mark(Country country);

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
