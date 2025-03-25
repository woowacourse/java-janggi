package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import janggi.piece.PieceType;
import janggi.piece.Side;
import java.util.List;

public class King extends LimitMovable {

    public King(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                createRoute(position, Direction.UP),
                createRoute(position, Direction.DOWN),
                createRoute(position, Direction.LEFT),
                createRoute(position, Direction.RIGHT)
        );
    }

    private Route createRoute(final Position position, final Direction direction) {
        Route route = new Route();
        route.addRoute(position.move(direction));
        return route;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
