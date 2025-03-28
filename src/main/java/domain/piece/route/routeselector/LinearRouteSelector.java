package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.List;

public class LinearRouteSelector implements RouteSelector {

    @Override
    public Route getRoute(final JanggiSide side, final List<Route> directions, final JanggiPosition origin, final JanggiPosition destination) {
        for (Route route : directions) {
            if (route.isSameDirectionWith(origin, destination)) {
                int moveCount = route.getMoveCount(origin, destination);
                return route.createNewRouteOf(moveCount);
            }
        }
        throw new InvalidPathException();
    }
}
