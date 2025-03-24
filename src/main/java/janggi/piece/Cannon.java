package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class Cannon implements Piece {

    private final Team team;
    private final Position position;
    private final List<Movement> movements = List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.RIGHT,
            Movement.LEFT
    );


    public Cannon(Team team, Position position) {
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
    public boolean isObstacle(List<Position> pathPositions) {
        return pathPositions.stream()
                .anyMatch(pathPosition -> pathPosition.equals(position));
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
