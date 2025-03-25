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

public class SangMovement extends PathMovement {

    public SangMovement() {
        super(Set.of(
            MoveVector.combine(LEFT, LEFT_UP, LEFT_UP),
            MoveVector.combine(LEFT, LEFT_DOWN, LEFT_DOWN),
            MoveVector.combine(RIGHT, RIGHT_UP, RIGHT_UP),
            MoveVector.combine(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            MoveVector.combine(UP, LEFT_UP, LEFT_UP),
            MoveVector.combine(UP, RIGHT_UP, RIGHT_UP),
            MoveVector.combine(DOWN, LEFT_DOWN, LEFT_DOWN),
            MoveVector.combine(DOWN, RIGHT_DOWN, RIGHT_DOWN)
        ));
    }

    protected Path findPath(final Coordinate departure, final Coordinate arrival) {
        int dx = arrival.x() - departure.x();
        int dy = arrival.y() - departure.y();

        final var coordinates = computeMovement(dx, dy)
            .stream()
            .map(departure::move)
            .toList();
        return new Path(coordinates);
    }

    private List<MoveVector> computeMovement(final int dx, final int dy) {
        if (dx == -3 && dy == -2) { //좌상
            return List.of(LEFT, MoveVector.combine(LEFT, LEFT_UP));
        }
        if (dx == -3 && dy == 2) { //좌하
            return List.of(LEFT, MoveVector.combine(LEFT, LEFT_DOWN));
        }
        if (dx == 3 && dy == -2) { //우상
            return List.of(RIGHT, MoveVector.combine(RIGHT, RIGHT_UP));
        }
        if (dx == 3 && dy == 2) { //우하
            return List.of(RIGHT, MoveVector.combine(RIGHT, RIGHT_DOWN));
        }

        if (dx == -2 && dy == -3) {//상좌
            return List.of(UP, MoveVector.combine(UP, LEFT_UP));
        }
        if (dx == -2 && dy == 3) {//하좌
            return List.of(DOWN, MoveVector.combine(DOWN, LEFT_DOWN));
        }
        if (dx == 2 && dy == -3) {//상우
            return List.of(UP, MoveVector.combine(UP, RIGHT_UP));
        }
        if (dx == 2 && dy == 3) {//하우
            return List.of(DOWN, MoveVector.combine(DOWN, RIGHT_DOWN));
        }

        throw new IllegalStateException("상이 움직일 수 없는 움직임입니다.");
    }
}
