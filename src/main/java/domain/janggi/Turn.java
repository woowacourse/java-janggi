package domain.janggi;

import java.util.Objects;

public class Turn {

    private Team team;

    public Turn(final Team team) {
        this.team = team;
    }

    public Team currentTeam() {
        return team;
    }

    public void change() {
        team = team.opposite();
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
