package janggi.domain.piece.limit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Direction;
import janggi.domain.move.Route;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Horse extends LimitMovable {

    public Horse(final Turn side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        List<Route> movableRoutes = new ArrayList<>();
        movableRoutes.addAll(createRoute(position, Direction.UP, Direction.LEFT_UP));
        movableRoutes.addAll(createRoute(position, Direction.UP, Direction.RIGHT_UP));
        movableRoutes.addAll(createRoute(position, Direction.LEFT, Direction.LEFT_UP));
        movableRoutes.addAll(createRoute(position, Direction.LEFT, Direction.LEFT_DOWN));
        movableRoutes.addAll(createRoute(position, Direction.RIGHT, Direction.RIGHT_UP));
        movableRoutes.addAll(createRoute(position, Direction.RIGHT, Direction.RIGHT_DOWN));
        movableRoutes.addAll(createRoute(position, Direction.DOWN, Direction.LEFT_DOWN));
        movableRoutes.addAll(createRoute(position, Direction.DOWN, Direction.RIGHT_DOWN));

        movableRoutes.removeIf(route -> route.getPositions().isEmpty());
        return movableRoutes;
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
