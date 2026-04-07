package domain.state;

import domain.enums.Country;

public class ExitGame implements State {
    @Override
    public State changeTurn() {
        return new ExitGame();
    }

    @Override
    public Country getCountry() {
        return Country.NONE;
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public String getValue(){
        return "Exit";
    }

}
