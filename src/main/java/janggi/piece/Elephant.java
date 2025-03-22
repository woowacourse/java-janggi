package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import java.util.List;

public class Elephant extends LimitMovable {

    public Elephant(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                createRoute(position, Direction.UP, Direction.LEFT_UP),
                createRoute(position, Direction.UP, Direction.RIGHT_UP),

                createRoute(position, Direction.LEFT, Direction.LEFT_UP),
                createRoute(position, Direction.LEFT, Direction.LEFT_DOWN),

                createRoute(position, Direction.RIGHT, Direction.RIGHT_UP),
                createRoute(position, Direction.RIGHT, Direction.RIGHT_DOWN),

                createRoute(position, Direction.DOWN, Direction.LEFT_DOWN),
                createRoute(position, Direction.DOWN, Direction.RIGHT_DOWN)
        );
    }

    private Route createRoute(final Position originalPosition, Direction normalDirection,
                              Direction diagonalNormalDirection) {
        Route route = new Route();
        Position movedPosition = originalPosition.move(normalDirection);
        Position movedPosition2 = movedPosition.move(diagonalNormalDirection);
        Position movedPosition3 = movedPosition2.move(diagonalNormalDirection);

        route.addRoute(movedPosition);
        route.addRoute(movedPosition2);
        route.addRoute(movedPosition3);

        return route;
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
