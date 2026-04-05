package domain.state;

import domain.enums.Country;

public interface State {
    State changeTurn();
    Country getCountry();
    boolean isGameOver();
    default State exitGame() {
        return new ExitGame();
    }
}
