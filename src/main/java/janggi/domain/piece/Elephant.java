package janggi.domain.piece;

import static janggi.domain.piece.direction.BoardSize.validateSize;
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

public class Elephant extends Piece {

    private static final List<List<Direction>> ELEPHANT_MOVES = List.of(
            List.of(UP, RIGHT_UP, RIGHT_UP),
            List.of(UP, LEFT_UP, LEFT_UP),
            List.of(RIGHT, RIGHT_UP, RIGHT_UP),
            List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            List.of(DOWN, LEFT_DOWN, LEFT_DOWN),
            List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),
            List.of(LEFT, LEFT_UP, LEFT_UP),
            List.of(LEFT, LEFT_DOWN, LEFT_DOWN)
    );

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Route> calculateRoutes() {
        return ELEPHANT_MOVES.stream()
                .map(this::calculateRoute)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Optional<Route> calculateRoute(final List<Direction> move) {
        int x = position.x();
        int y = position.y();
        final List<Position> positions = new ArrayList<>();

        for (final Direction direction : move) {
            x += direction.dx();
            y += direction.dy();
            if (validateSize(x, y)) {
                positions.add(new Position(x, y));
            }
        }
        return Optional.of(new Route(positions));
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
