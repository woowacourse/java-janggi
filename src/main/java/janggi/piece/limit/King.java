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
        List<Route> movableRoutes = new ArrayList<>();

        movableRoutes.addAll(createStraightRoute(position, Direction.UP));
        movableRoutes.addAll(createStraightRoute(position, Direction.DOWN));
        movableRoutes.addAll(createStraightRoute(position, Direction.LEFT));
        movableRoutes.addAll(createStraightRoute(position, Direction.RIGHT));

        movableRoutes.addAll(createDiagonalRoute(position, Direction.LEFT_UP));
        movableRoutes.addAll(createDiagonalRoute(position, Direction.LEFT_DOWN));
        movableRoutes.addAll(createDiagonalRoute(position, Direction.RIGHT_UP));
        movableRoutes.addAll(createDiagonalRoute(position, Direction.RIGHT_DOWN));

        movableRoutes.removeIf(route -> route.getPositions().isEmpty());
        return movableRoutes;
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
        return PieceType.KING;
    }
}
