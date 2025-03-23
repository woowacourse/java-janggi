package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Objects;

public class Chariot implements Piece {

    private final Team team;
    private final Position position;

    public Chariot(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chariot chariot = (Chariot) o;
        return team == chariot.team && Objects.equals(position, chariot.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
