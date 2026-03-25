package janggi.domain;

import janggi.domain.side.TeamType;
import java.util.List;

public class Turns {

    private final List<Turn> value;

    public Turns(List<Turn> value) {
        this.value = value;
    }

    public TeamType getFirstTurn() {
        return TeamType.CHU;
    }
}
