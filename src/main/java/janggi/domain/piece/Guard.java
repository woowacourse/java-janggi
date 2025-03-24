package janggi.domain.piece;

import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.UP;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard extends Piece {

    private static final List<List<Direction>> GUARD_MOVES = List.of(
            List.of(UP),
            List.of(DOWN),
            List.of(LEFT),
            List.of(RIGHT)
    );

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    public Set<Route> calculateIndependentRoutes() {
        return GUARD_MOVES.stream()
                .map(this::calculateRoute)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Optional<Route> calculateRoute(final List<Direction> move) {
        final List<Position> positions = new ArrayList<>();

        Position currentPosition = position;
        for (final Direction direction : move) {
            if (currentPosition.canMove(direction)) {
                final Position nextPosition = currentPosition.move(direction);
                positions.add(nextPosition);
                currentPosition = nextPosition;
                continue;
            }
            return Optional.empty();
        }
        return Optional.of(new Route(positions));
    }
}
