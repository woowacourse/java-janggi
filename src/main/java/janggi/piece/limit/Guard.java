package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.List;

public class Guard extends LimitMovable {

    public Guard(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        List<Route> movableRoute = new ArrayList<>();

        movableRoute.addAll(createStraightRoute(position, Direction.UP));
        movableRoute.addAll(createStraightRoute(position, Direction.DOWN));
        movableRoute.addAll(createStraightRoute(position, Direction.LEFT));
        movableRoute.addAll(createStraightRoute(position, Direction.RIGHT));

        movableRoute.addAll(createDiagonalRoute(position, Direction.LEFT_UP));
        movableRoute.addAll(createDiagonalRoute(position, Direction.LEFT_DOWN));
        movableRoute.addAll(createDiagonalRoute(position, Direction.RIGHT_UP));
        movableRoute.addAll(createDiagonalRoute(position, Direction.RIGHT_DOWN));

        return movableRoute;
    }

    private List<Route> createStraightRoute(final Position position, final Direction direction) {
        Position movedPosition = position.move(direction);
        if (movedPosition.isInBoardRange()) {
            return List.of(new Route(movedPosition));
        }
        return List.of();
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
        return PieceType.GUARD;
    }
}
