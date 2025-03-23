package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Objects;

public class Guard implements Piece {

    private final Team team;
    private final Position position;

    public Guard(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guard guard = (Guard) o;
        return team == guard.team && Objects.equals(position, guard.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
