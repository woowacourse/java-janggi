package domain.state;

import domain.constant.Country;

public final class StateFactory {
    public static State from(Country country) {
        if (country.equals(Country.CHO)) {
            return new ChoTurn();
        }
        return new HanTurn();
    }
}
