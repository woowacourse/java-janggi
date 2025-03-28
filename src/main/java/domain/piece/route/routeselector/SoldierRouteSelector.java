package domain.piece.route.routeselector;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.List;

public class SoldierRouteSelector implements RouteSelector {

    private static final List<MovingPattern> SOLDIER_OF_CHO_DIRECTIONS = List.of(
            MovingPattern.RIGHT,
            MovingPattern.LEFT,
            MovingPattern.UP,
            MovingPattern.DIAGONAL_UP_RIGHT,
            MovingPattern.DIAGONAL_UP_LEFT
    );
    private static final List<MovingPattern> SOLDIER_OF_HAN_DIRECTIONS = List.of(
            MovingPattern.RIGHT,
            MovingPattern.LEFT,
            MovingPattern.DOWN,
            MovingPattern.DIAGONAL_DOWN_LEFT,
            MovingPattern.DIAGONAL_DOWN_RIGHT
    );

    @Override
    public Route getRoute(final JanggiSide side, final List<Route> routes, final JanggiPosition origin, final JanggiPosition destination) {
        Route moveDirections = routes.stream()
                .filter(route -> route.isReachableByRoute(origin, destination))
                .findFirst()
                .orElseThrow(InvalidPathException::new);

        checkValidRouteOfSide(side, moveDirections);
        return moveDirections;
    }


    private void checkValidRouteOfSide(final JanggiSide side, final Route route) {
        if (side == JanggiSide.CHO && !route.isDirectionContainsIn(SOLDIER_OF_CHO_DIRECTIONS)) {
            throw new InvalidPathException();
        }

        if (side == JanggiSide.HAN && !route.isDirectionContainsIn(SOLDIER_OF_HAN_DIRECTIONS)) {
            throw new InvalidPathException();
        }
    }
}
