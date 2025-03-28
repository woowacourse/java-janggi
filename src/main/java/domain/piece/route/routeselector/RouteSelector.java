package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.List;

public interface RouteSelector {

    Route getRoute(JanggiSide side, List<Route> routes, JanggiPosition origin, JanggiPosition destination);
}
