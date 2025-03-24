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

    @Override
    public List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board) {
        List<Route> candidateRoutes = computeCandidatePositions(position);
        List<Position> reachableDestinations = new ArrayList<>();
        for (Route route : candidateRoutes) {
            List<Position> positions = route.getPositions();
            reachableDestinations.addAll(addValidDestination(positions, board));
        }
        return reachableDestinations;
    }

    protected abstract List<Position> addValidDestination(final List<Position> positions, final Map<Position, Piece> board);

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
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }
}
