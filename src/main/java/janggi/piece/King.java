package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class King implements Piece {

    private final Team team;
    private final Position position;
    private final List<Movement> movements= List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.RIGHT,
            Movement.LEFT,
            Movement.RIGHT_UP,
            Movement.RIGHT_DOWN,
            Movement.LEFT_UP,
            Movement.LEFT_DOWN
    );

    public King(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    public void calculatePossibleMovement() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        King king = (King) o;
        return team == king.team && Objects.equals(position, king.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
