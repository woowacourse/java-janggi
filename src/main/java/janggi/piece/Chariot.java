package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class Chariot implements Piece {

    private final Team team;
    private final Position position;

    public Chariot(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public void move(Position arrivedPosition, List<Piece> positioningPiece) {

    }

    @Override
    public boolean isSameTeam(Team team) {
        return false;
    }

    @Override
    public boolean matchesPosition(Position position) {
        return false;
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
