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

public class Sang extends LimitedMovementPiece {

    public Sang(Team team) {
        super(
            team,
            Set.of(
                Movement.combine(LEFT, LEFT_UP, LEFT_UP),
                Movement.combine(LEFT, LEFT_DOWN, LEFT_DOWN),
                Movement.combine(RIGHT, RIGHT_UP, RIGHT_UP),
                Movement.combine(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
                Movement.combine(UP, LEFT_UP, LEFT_UP),
                Movement.combine(UP, RIGHT_UP, RIGHT_UP),
                Movement.combine(DOWN, LEFT_DOWN, LEFT_DOWN),
                Movement.combine(DOWN, RIGHT_DOWN, RIGHT_DOWN)
            )
        );
    }

    @Override
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        return computeMovement(dx, dy)
            .stream()
            .map(departure::move)
            .toList();
    }

    private List<Movement> computeMovement(final int dx, final int dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx < 0 && dy < 0) {
                //좌상
                return List.of(LEFT, Movement.combine(LEFT, LEFT_UP));
            } else if (dx < 0 && dy > 0) {
                //좌하
                return List.of(LEFT, Movement.combine(LEFT, LEFT_DOWN));
            } else if (dx > 0 && dy < 0) {
                //우상
                return List.of(RIGHT, Movement.combine(RIGHT, RIGHT_UP));
            } else if (dx > 0 && dy > 0) {
                //우하
                return List.of(RIGHT, Movement.combine(RIGHT, RIGHT_DOWN));
            }
        }

        if (dx < 0 && dy < 0) {
            //상좌
            return List.of(UP, Movement.combine(UP, LEFT_UP));
        } else if (dx < 0 && dy > 0) {
            //하좌
            return List.of(DOWN, Movement.combine(DOWN, LEFT_DOWN));
        } else if (dx > 0 && dy < 0) {
            //상우
            return List.of(UP, Movement.combine(UP, RIGHT_UP));
        } else if (dx > 0 && dy > 0) {
            //하우
            return List.of(DOWN, Movement.combine(DOWN, RIGHT_DOWN));
        }
        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
