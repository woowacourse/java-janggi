package janggi.piece;

import janggi.board.Direction;
import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.board.Route;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {

    private static final int ALLOWED_MOVE = 1;

    public Soldier(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        if (isCho()) {
            return computeAndExcludeInvalidRoute(position, Direction.DOWN);
        }
        return computeAndExcludeInvalidRoute(position, Direction.UP);
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

    @Override
    public String getSymbol() {
        return "J";
    }

    private List<Route> computeAndExcludeInvalidRoute(final Position position, final Direction direction) {
        List<Route> routes = computeStraightRoutes(position, ALLOWED_MOVE);
        Position invalidPosition = position.move(direction);
        return routes.stream()
                .filter(route -> !route.getDestination().equals(invalidPosition))
                .toList();
    }

}
