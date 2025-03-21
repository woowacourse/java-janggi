package janggi.piece;

import janggi.board.Direction;
import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {

        Route rightRoute = computeStraightLimitRoute(position, Direction.RIGHT);
        Route leftRoute = computeStraightLimitRoute(position, Direction.LEFT);
        Route upRoute = computeStraightLimitRoute(position, Direction.UP);
        Route downRoute = computeStraightLimitRoute(position, Direction.DOWN);

        return List.of(rightRoute, leftRoute, upRoute, downRoute);
    }

    @Override
    public String getSymbol() {
        return "C";
    }

    private Route computeStraightLimitRoute(final Position position, final Direction direction) {
        Route route = new Route(position.move(direction));
        for (int delta = 1; delta < MOVE_LIMIT; delta++) {
            Position destination = route.getDestination();
            route.addRoute(destination.move(direction));
        }
        return route;
    }

}
