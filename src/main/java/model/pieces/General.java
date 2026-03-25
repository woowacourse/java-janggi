package model.pieces;

import model.Country;

public class General extends Piece {
    private static final String MARK = "장";

    public General(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }
}
