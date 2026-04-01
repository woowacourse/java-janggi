package domain;

public class ChoTurn implements State {
    @Override
    public State changeTurn() {
        return new HanTurn();
    }

    @Override
    public Country getCountry() {
        return Country.CHO;
    }
}
