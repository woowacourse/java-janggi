package model;

public abstract class Piece {
    private final Country country;

    protected Piece(Country country) {
        this.country = country;
    }
}
