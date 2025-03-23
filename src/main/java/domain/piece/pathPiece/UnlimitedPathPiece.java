package domain.piece.pathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.ArrayList;
import java.util.Set;

public abstract class UnlimitedPathPiece extends PathPiece {

    public UnlimitedPathPiece(final Team team, final Coordinate coordinate, final Set<Movement> movements) {
        super(team, coordinate, movements);
    }

    protected final Path findPath(final Coordinate arrival) {
        for (final var movement : movements()) {
            var current = this.coordinate;
            final var path = new ArrayList<Coordinate>();

            while (canMoveOneStep(movement, current)) {
                var next = current.move(movement);
                if (next.equals(arrival)) {
                    return new Path(path);
                }

                path.add(next);
                current = next;
            }
        }
        return Path.unreachable();
    }

    /**
     * x.x
     * ...
     * ...
     * 궁성 내 x에서 좌/우측 하단으로 이동할 때 궁성 모서리에 도착하면 더이상 진행 방향이 유효하지 않음.
     */
    private boolean canMoveOneStep(final Movement movement, final Coordinate current) {
        final var moved = moveTo(current);

        boolean isStillAvailableMovement = moved.movements().contains(movement);
        return isStillAvailableMovement && current.canMove(movement);
    }
}
