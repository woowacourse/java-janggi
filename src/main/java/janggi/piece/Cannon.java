package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Objects;

public class Cannon implements Piece {

    private final Team team;
    private final Position position;

    public Cannon(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cannon cannon = (Cannon) o;
        return team == cannon.team && Objects.equals(position, cannon.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
