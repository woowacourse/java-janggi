package model.pieces;

import model.Country;

public class Soldier extends Piece {
    private static final String MARK = "졸";

    public Soldier(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }
}
