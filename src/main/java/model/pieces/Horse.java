package model.pieces;

import model.Country;

public class Horse extends Piece {
    private static final String MARK = "마";

    public Horse(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }
}
