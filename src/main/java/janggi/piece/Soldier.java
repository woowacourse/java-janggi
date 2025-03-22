package janggi.piece;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;

import java.util.List;
import java.util.Map;

public class Soldier extends LimitMovable {

    public Soldier(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        if (isCho()) {
            return moveCho(position);
        }
        return moveHan(position);
    }

    private List<Route> moveCho(final Position position) {
        return List.of(
                createRoute(position, Direction.LEFT),
                createRoute(position, Direction.UP),
                createRoute(position, Direction.RIGHT)
        );
    }

    private List<Route> moveHan(final Position position) {
        return List.of(
                createRoute(position, Direction.LEFT),
                createRoute(position, Direction.DOWN),
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
        return PieceType.SOLDIER;
    }
}
