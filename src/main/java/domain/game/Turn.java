package domain.game;

import domain.piece.Country;

public class Turn {

    private Country current;

    public Turn(Country current) {
        this.current = current;
    }

    public void next() {
        current = current.convertCountry();
    }

    public String getCurrentName() {
        return current.getCountryName();
    }

    public Country getCountry() {
        return current;
    }
}
