package domain;

public interface State {
    State changeTurn();
    Country getCountry();
}
