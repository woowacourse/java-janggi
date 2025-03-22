package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import janggi.piece.Piece;
import janggi.piece.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class UnLimitMovable implements Piece {
    private static final int MOVE_LIMIT = 10;

    private final Side side;

    public UnLimitMovable(Side side) {
        this.side = side;
    }

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
    public List<Position> filterReachableDestinations(final List<Route> routes, final Map<Position, Piece> board) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            addValidDestination(positions, reachablePositions, board);
        }
        return reachablePositions;
    }

    protected abstract void addValidDestination(final List<Position> positions, final List<Position> reachablePositions,
                                                final Map<Position, Piece> board);

    @Override
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }
}
