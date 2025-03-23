package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Objects;

public class Elephant implements Piece {

    private final Team team;
    private final Position position;

    public Elephant(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Elephant elephant = (Elephant) o;
        return team == elephant.team && Objects.equals(position, elephant.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
