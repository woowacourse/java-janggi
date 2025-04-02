package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.List;

import static domain.MovingPattern.*;

public class PalaceLinearRouteSelector implements RouteSelector {

    private static final PalaceLinearRouteSelector INSTANCE = new PalaceLinearRouteSelector();
    private final List<Route> movableDirectionsInPalace = List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_UP_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT)
    );

    private PalaceLinearRouteSelector() {
    }

    public static RouteSelector getInstance() {
        return INSTANCE;
    }

    @Override
    public Route getRoute(final JanggiSide side, final JanggiPosition origin, final JanggiPosition destination) {
        if (!isMoveInPalace(origin, destination)) {
            return Route.createEmptyRoute();
        }
        for (Route direction : movableDirectionsInPalace) {
            if (direction.isSameDirectionWith(origin, destination)) {
                int moveCount = direction.getMoveCount(origin, destination);
                return direction.createNewRouteOf(moveCount);
            }
        }
        return Route.createEmptyRoute();
    }

    private boolean isMoveInPalace(JanggiPosition origin, JanggiPosition destination) {
        return origin.isDiagonalMovablePalace() && destination.isDiagonalMovablePalace();
    }
}
