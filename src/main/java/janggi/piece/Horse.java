package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Objects;

public class Horse implements Piece {

    private final Team team;
    private final Position position;

    public Horse(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return Objects.equals(position, horse.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
