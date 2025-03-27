package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.List;

public class Horse extends LimitMovable {

    public Horse(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        List<Route> routes = new ArrayList<>();
        routes.addAll(createRoute(position, Direction.UP, Direction.LEFT_UP));
        routes.addAll(createRoute(position, Direction.UP, Direction.RIGHT_UP));
        routes.addAll(createRoute(position, Direction.LEFT, Direction.LEFT_UP));
        routes.addAll(createRoute(position, Direction.LEFT, Direction.LEFT_DOWN));
        routes.addAll(createRoute(position, Direction.RIGHT, Direction.RIGHT_UP));
        routes.addAll(createRoute(position, Direction.RIGHT, Direction.RIGHT_DOWN));
        routes.addAll(createRoute(position, Direction.DOWN, Direction.LEFT_DOWN));
        routes.addAll(createRoute(position, Direction.DOWN, Direction.RIGHT_DOWN));

        return routes;
    }

    private List<Route> createRoute(final Position originalPosition, final Direction normalDirection,
                              final Direction diagonalNormalDirection) {
        Position movedPosition = originalPosition.move(normalDirection);
        Position diagonalMovedPosition = movedPosition.move(diagonalNormalDirection);

        if (movedPosition.isInBoardRange() && diagonalMovedPosition.isInBoardRange()) {
            return List.of(new Route(movedPosition, diagonalMovedPosition));
        }

        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}
