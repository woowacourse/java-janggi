package model.pieces;

import model.Country;

public class Guard extends Piece {
    private static final String MARK = "사";

    public Guard(Country country) {
        super(country);
    }

    @Override
    public String mark(Country country) {
        return country.color() + MARK + Country.RESET;
    }
}
