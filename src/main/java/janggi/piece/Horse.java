package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class Horse implements Piece {

    private final Team team;
    private final Position position;
    private final List<Movement> movements = List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.RIGHT,
            Movement.LEFT,
            Movement.RIGHT_RIGHT_UP,
            Movement.RIGHT_RIGHT_DOWN,
            Movement.LEFT_LEFT_UP,
            Movement.LEFT_LEFT_DOWN
    );

    private final List<Movement> availablePathMovement = List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.RIGHT,
            Movement.LEFT
    );


    public Horse(Team team, Position position) {
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
        Horse horse = (Horse) o;
        return team == horse.team && Objects.equals(position, horse.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }

    @Override
    public String toString() {
        return "[" + team +
                ": " + position;
    }
}
