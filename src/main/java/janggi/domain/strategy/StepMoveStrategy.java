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

public class StepMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDirection : baseDirections) {
            addStepPath(current, baseDirection, paths);
        }
        return paths;
    }

    private void addStepPath(Position current, Direction baseDirection, Paths paths) {
        if (current.canMove(baseDirection)) {
            Path path = new Path();
            path.add(baseDirection.move(current));
            paths.addPath(path);
        }
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
        Piece target = state.get(destination);

        if (target == null || !target.isSameSide(me)) {
            destinations.add(destination);
        }
    }
}
