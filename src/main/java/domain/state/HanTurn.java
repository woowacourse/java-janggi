package domain.state;

import domain.enums.Country;

public class HanTurn implements State{
    @Override
    public State changeTurn() {
        return new ChoTurn();
    }

    @Override
    public Country getCountry() {
        return Country.HAN;
    }
}
