package janggi.piece;

import janggi.board.Direction;
import janggi.board.Position;
import janggi.board.Route;
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
