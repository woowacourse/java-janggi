package model.pieces;

import model.Country;

public class Elephant extends Piece {
    private static final String MARK = "상";

    public Elephant(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }
}
