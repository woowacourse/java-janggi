package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import java.util.Objects;

public class Turn {
    private Team team;

    public Turn(final Team team) {
        this.team = team;
    }

    public static Turn initialize() {
        return new Turn(BLUE);
    }


    public Team getTurn() {
        return team;
    }

    public void changeTurn() {
        if (team == BLUE) {
            team = RED;
            return;
        }
        team = BLUE;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Turn turn = (Turn) o;
        return team == turn.team;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(team);
    }
}
