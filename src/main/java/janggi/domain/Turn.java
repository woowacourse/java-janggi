package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

public class Turn {
    private Team team;

    public Turn(final Team team) {
        this.team = team;
    }

    public static Turn initialize() {
        return new Turn(BLUE);
    }


    public Team getCurrentTurn() {
        return team;
    }

    public void changeTurn() {
        if (team == BLUE) {
            team = RED;
            return;
        }
        team = BLUE;
    }
}
