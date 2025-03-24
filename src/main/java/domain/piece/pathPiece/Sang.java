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

public class Sang extends PathPiece {

    public Sang(Team team, Coordinate coordinate) {
        super(
            team,
            coordinate,
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
    public Piece moveTo(final Coordinate arrival) {
        return new Sang(team, arrival);
    }

    protected Path findPath(Coordinate arrival) {
        int dx = arrival.x() - coordinate.x();
        int dy = arrival.y() - coordinate.y();

        final var coordinates = computeMovement(dx, dy)
            .stream()
            .map(coordinate::move)
            .toList();
        return new Path(coordinates);
    }

    private List<Movement> computeMovement(final int dx, final int dy) {
        if (dx == -3 && dy == -2) { //좌상
            return List.of(LEFT, Movement.combine(LEFT, LEFT_UP));
        }
        if (dx == -3 && dy == 2) { //좌하
            return List.of(LEFT, Movement.combine(LEFT, LEFT_DOWN));
        }
        if (dx == 3 && dy == -2) { //우상
            return List.of(RIGHT, Movement.combine(RIGHT, RIGHT_UP));
        }
        if (dx == 3 && dy == 2) { //우하
            return List.of(RIGHT, Movement.combine(RIGHT, RIGHT_DOWN));
        }

        if (dx == -2 && dy == -3) {//상좌
            return List.of(UP, Movement.combine(UP, LEFT_UP));
        }
        if (dx == -2 && dy == 3) {//하좌
            return List.of(DOWN, Movement.combine(DOWN, LEFT_DOWN));
        }
        if (dx == 2 && dy == -3) {//상우
            return List.of(UP, Movement.combine(UP, RIGHT_UP));
        }
        if (dx == 2 && dy == 3) {//하우
            return List.of(DOWN, Movement.combine(DOWN, RIGHT_DOWN));
        }

        throw new IllegalStateException("상이 움직일 수 없는 움직임입니다.");
    }
}
