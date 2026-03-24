package model.pieces;

import model.Country;

public abstract class Piece {
    private final Country country;
    public Piece(Country country) {
        this.country = country;
    }
}
