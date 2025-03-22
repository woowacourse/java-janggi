package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import java.util.List;
import java.util.Map;

public abstract class UnLimitMovable implements Piece{
    private static final int MOVE_LIMIT = 10;

    public List<Route> computeCandidatePositions(Position position) {
        Route upRoute = createRoute(position, Direction.UP);
        Route downRoute = createRoute(position, Direction.DOWN);
        Route leftRoute = createRoute(position, Direction.LEFT);
        Route rightRoute = createRoute(position, Direction.RIGHT);

        return List.of(rightRoute, leftRoute, upRoute, downRoute);
    }

    private Route createRoute(Position position, Direction direction) {
        Route route = new Route(position);

        for (int i = 0; i < MOVE_LIMIT; i++) {
            Position lastPosition = route.getLastPosition();
            route.addRoute(lastPosition.move(direction));
        }
        route.deleteFirstPosition();
        return route;
    }
}
