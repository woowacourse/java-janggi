package domain.state;

import domain.enums.Country;

public interface State {
    State changeTurn();
    Country getCountry();
    default State exitGame() {
        return new ExitGame();
    }
}
