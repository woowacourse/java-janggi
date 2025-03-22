package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import java.util.List;

public class Guard extends LimitMovable {

    public Guard(final Side side) {
        super(side);
    }

    public List<Route> computeCandidatePositions(Position position) {
        return List.of(
                createRoute(position, Direction.UP),
                createRoute(position, Direction.RIGHT),
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
        return PieceType.GUARD;
    }
}
