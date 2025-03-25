package domain.piece.pathMovement;

import static domain.MoveVector.DOWN;
import static domain.MoveVector.LEFT;
import static domain.MoveVector.LEFT_DOWN;
import static domain.MoveVector.LEFT_UP;
import static domain.MoveVector.RIGHT;
import static domain.MoveVector.RIGHT_DOWN;
import static domain.MoveVector.RIGHT_UP;
import static domain.MoveVector.UP;

import domain.Coordinate;
import domain.MoveVector;
import java.util.List;
import java.util.Set;

public class MaMovement extends PathMovement {

    public MaMovement() {
        super(Set.of(
            MoveVector.combine(LEFT, LEFT_UP),
            MoveVector.combine(LEFT, LEFT_DOWN),
            MoveVector.combine(RIGHT, RIGHT_UP),
            MoveVector.combine(RIGHT, RIGHT_DOWN),
            MoveVector.combine(UP, LEFT_UP),
            MoveVector.combine(UP, RIGHT_UP),
            MoveVector.combine(DOWN, LEFT_DOWN),
            MoveVector.combine(DOWN, RIGHT_DOWN)
        ));
    }

    public Path findPath(final Coordinate departure, final Coordinate arrival) {
        int dx = arrival.x() - departure.x();
        int dy = arrival.y() - departure.y();

        final var movement = computeMovement(dx, dy);
        return new Path(List.of(departure.move(movement)));
    }

    private MoveVector computeMovement(final int dx, final int dy) {
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
