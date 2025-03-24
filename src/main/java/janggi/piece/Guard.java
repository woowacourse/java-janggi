package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class Guard implements Piece {

    private final Team team;
    private final Position position;
    private final List<Movement> movements = List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.RIGHT,
            Movement.LEFT,
            Movement.RIGHT_UP,
            Movement.RIGHT_DOWN,
            Movement.LEFT_UP,
            Movement.LEFT_DOWN
    );

    public Guard(Team team, Position position) {
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
        Guard guard = (Guard) o;
        return team == guard.team && Objects.equals(position, guard.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
