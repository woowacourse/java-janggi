package model.pieces;

import model.Country;

public class Chariot extends Piece {
    private static final String MARK = "차";

    public Chariot(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }

}
