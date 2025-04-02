package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

public interface RouteSelector {

    Route getRoute(final JanggiSide side, JanggiPosition origin, JanggiPosition destination);
}
