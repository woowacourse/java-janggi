package model.pieces;

import model.Country;

public class Cannon extends Piece {
    private static final String MARK = "포";

    public Cannon(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }
}
