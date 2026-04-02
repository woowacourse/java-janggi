package domain.state;

import domain.constant.Country;

public interface State {
    State changeTurn();
    Country getCountry();
}
