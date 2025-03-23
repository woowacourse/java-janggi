package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Arrays;
import java.util.Objects;

public class Elephant implements Piece{

    private final Position position;

    public Elephant(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Team getTeam() {
        return null;
    }

    @Override
    public boolean isOccupiedByMe(Position position) {
        return false;
    }

    @Override
    public void move(Position position) {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Elephant elephant = (Elephant) o;
        return Objects.equals(position, elephant.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
