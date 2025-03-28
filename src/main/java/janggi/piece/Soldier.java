package janggi.piece;

import janggi.board.Direction;
import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Side side) {
        super(Symbol.SOLDIER, side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        List<Position> positions = position.moveToCandidate();
        List<Route> routes = Route.createRoutes(positions);
        if (isCho()) {
            return excludeInvalidRoutes(routes, position, Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
        }
        return excludeInvalidRoutes(routes, position, Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT);
    }

    @Override
    public List<Position> filterReachableDestinations(final List<Route> candidateRoutes, final JanggiBoard board) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : candidateRoutes) {
            Position destination = route.getDestination();
            if (board.isOutOfRange(destination)) {
                continue;
            }
            if (board.checkInvalidIntermediatePositions(route)) {
                continue;
            }
            if (isAllyWith(board.findPieceBy(destination))) {
                continue;
            }
            reachablePositions.add(destination);
        }
        return reachablePositions;
    }

    private List<Route> excludeInvalidRoutes(final List<Route> routes, final Position position,
                                             final Direction... directions) {
        List<Position> invalidPositions = position.movesTo(directions);
        return routes.stream()
                .filter(route -> !invalidPositions.contains(route.getDestination()))
                .toList();
    }

}
