package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;

import java.util.List;

public class Chariot implements Piece {

    private static final int MOVE_LIMIT = 10;

    private final Side side;

    public Chariot(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        Route upRoute = createRoute(position, Direction.UP);
        Route downRoute = createRoute(position, Direction.DOWN);
        Route leftRoute = createRoute(position, Direction.LEFT);
        Route rightRoute = createRoute(position, Direction.RIGHT);

        return List.of(rightRoute, leftRoute, upRoute, downRoute);
    }

    private Route createRoute(final Position position, final Direction direction) {
        Route route = new Route(position);

        for (int i = 0; i < MOVE_LIMIT; i++) {
            Position lastPosition = route.getLastPosition();
            route.addRoute(lastPosition.move(direction));
        }
        route.deleteFirstPosition();
        return route;
    }

    @Override
    public String getSymbol() {
        return "C";
    }

    @Override
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }
}
