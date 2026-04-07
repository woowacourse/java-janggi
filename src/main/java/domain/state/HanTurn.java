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

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getValue(){
        return "HanTurn";
    }

}
