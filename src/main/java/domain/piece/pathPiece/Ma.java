package domain.piece.pathPiece;

import static domain.Movement.DOWN;
import static domain.Movement.LEFT;
import static domain.Movement.LEFT_DOWN;
import static domain.Movement.LEFT_UP;
import static domain.Movement.RIGHT;
import static domain.Movement.RIGHT_DOWN;
import static domain.Movement.RIGHT_UP;
import static domain.Movement.UP;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.piece.Piece;
import java.util.List;
import java.util.Set;

public class Ma extends PathPiece {

    public Ma(Team team, Coordinate coordinate) {
        super(
            team,
            coordinate,
            Set.of(
                Movement.combine(LEFT, LEFT_UP),
                Movement.combine(LEFT, LEFT_DOWN),
                Movement.combine(RIGHT, RIGHT_UP),
                Movement.combine(RIGHT, RIGHT_DOWN),
                Movement.combine(UP, LEFT_UP),
                Movement.combine(UP, RIGHT_UP),
                Movement.combine(DOWN, LEFT_DOWN),
                Movement.combine(DOWN, RIGHT_DOWN)
            )
        );
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Ma(team, arrival);
    }

    public Path findPath(Coordinate arrival) {
        int dx = arrival.getX() - coordinate.getX();
        int dy = arrival.getY() - coordinate.getY();

        final var movement = computeMovement(dx, dy);
        return new Path(List.of(coordinate.move(movement)));
    }

    private Movement computeMovement(final int dx, final int dy) {
        if (dx == -2 && Math.abs(dy) == 1) {
            return LEFT;
        }
        if (dx == 2 && Math.abs(dy) == 1) {
            return RIGHT;
        }
        if (dy == -2 && Math.abs(dx) == 1) {
            return UP;
        }
        if (dy == 2 && Math.abs(dx) == 1) {
            return DOWN;
        }
        throw new IllegalArgumentException("마가 움직일 수 없는 움직임입니다.");
    }
}
