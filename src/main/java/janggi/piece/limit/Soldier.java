package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Direction;
import janggi.move.Route;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<Route> movableRoute = new ArrayList<>(
                Arrays.asList(createRoute(position, Direction.LEFT),
                createRoute(position, Direction.UP),
                createRoute(position, Direction.RIGHT))
        );

        if (position.isInPalace()) {
            movableRoute.addAll(createRouteInPalace(position, Direction.LEFT_UP));
            movableRoute.addAll(createRouteInPalace(position, Direction.RIGHT_UP));
        }

        return movableRoute;
    }

    private List<Route> moveHan(final Position position) {
        List<Route> movableRoute = new ArrayList<>(
                Arrays.asList(createRoute(position, Direction.LEFT),
                        createRoute(position, Direction.DOWN),
                        createRoute(position, Direction.RIGHT))
        );

        if (position.isInPalace()) {
            movableRoute.addAll(createRouteInPalace(position, Direction.LEFT_DOWN));
            movableRoute.addAll(createRouteInPalace(position, Direction.RIGHT_DOWN));
        }

        return movableRoute;
    }

    private Route createRoute(final Position position, final Direction direction) {
        Route route = new Route();
        route.addRoute(position.move(direction));
        return route;
    }

    private List<Route> createRouteInPalace(final Position position, final Direction direction) {
        Position movedPosition = position.move(direction);
        if (movedPosition.isInPalace()){
            return List.of(new Route(movedPosition));
        }
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
