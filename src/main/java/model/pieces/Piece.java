package model.pieces;

import model.Country;

public abstract class Piece {
    private final Country country;
    protected Piece(Country country) {
        this.country = country;
    }
}
