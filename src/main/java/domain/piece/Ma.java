package domain.piece;

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
import java.util.List;
import java.util.Set;

public class Ma extends LimitedMovementPiece {

    public Ma(Team team) {
        super(
            team,
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
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        final var movement = computeMovement(dx, dy);
        return List.of(departure.move(movement));
    }

    private Movement computeMovement(final int dx, final int dy) {
        if (Math.abs(dx) == 2) {
            if (dx < 0) {
                return LEFT;
            }
            return RIGHT;
        }

        if (dy < 0) {
            return UP;
        }
        return DOWN;
    }
}
