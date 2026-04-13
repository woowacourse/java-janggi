package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StepMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction direction : baseDirections) {
            addStepPath(current, direction, paths);
        }

        return paths;
    }

    private void addStepPath(Position current, Direction baseDirection, Paths paths) {
        current.tryMove(baseDirection).ifPresent(nextPosition -> {
            Path path = new Path();
            path.add(nextPosition);
            paths.addPath(path);
        });
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateStepPath(route, boardState, destinations, movingPiece);
        }

        return destinations;
    }

    private void validateStepPath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Position destination = route.iterator().next();
        Optional<Piece> target = Optional.ofNullable(state.get(destination));
        if (target.isEmpty() || !target.get().isSameSide(me)) {
            destinations.add(destination);
        }
    }
}
