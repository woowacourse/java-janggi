package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.List;

public class King extends LimitMovable {

    public King(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        List<Route> movableRoute = new ArrayList<>();

        movableRoute.add(createStraightRoute(position, Direction.UP));
        movableRoute.add(createStraightRoute(position, Direction.DOWN));
        movableRoute.add(createStraightRoute(position, Direction.LEFT));
        movableRoute.add(createStraightRoute(position, Direction.RIGHT));

        movableRoute.addAll(createDiagonalRoute(position, Direction.LEFT_UP));
        movableRoute.addAll(createDiagonalRoute(position, Direction.LEFT_DOWN));
        movableRoute.addAll(createDiagonalRoute(position, Direction.RIGHT_UP));
        movableRoute.addAll(createDiagonalRoute(position, Direction.RIGHT_DOWN));

        return movableRoute;
    }

    private Route createStraightRoute(final Position position, final Direction direction) {
        Route route = new Route();
        route.addRoute(position.move(direction));
        return route;
    }

    private List<Route> createDiagonalRoute(final Position position, final Direction direction) {
        Position movedPosition = position.move(direction);
        if (movedPosition.isInPalace()){
            return List.of(new Route(movedPosition));
        }
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
