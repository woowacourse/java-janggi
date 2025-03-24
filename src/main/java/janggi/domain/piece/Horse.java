package janggi.domain.piece;

import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.LEFT_DOWN;
import static janggi.domain.piece.direction.Direction.LEFT_UP;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.RIGHT_DOWN;
import static janggi.domain.piece.direction.Direction.RIGHT_UP;
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

public class Horse extends Piece {

    private static final List<List<Direction>> HORSE_MOVES = List.of(
            List.of(RIGHT, RIGHT_UP),
            List.of(RIGHT, RIGHT_DOWN),

            List.of(LEFT, LEFT_UP),
            List.of(LEFT, LEFT_DOWN),

            List.of(UP, RIGHT_UP),
            List.of(UP, LEFT_UP),

            List.of(DOWN, RIGHT_DOWN),
            List.of(DOWN, LEFT_DOWN)
    );

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    public Set<Route> calculateIndependentRoutes() {
        return HORSE_MOVES.stream()
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
