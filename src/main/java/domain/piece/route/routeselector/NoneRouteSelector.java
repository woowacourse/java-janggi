package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.PieceNotExistException;

import java.util.List;

public class NoneRouteSelector implements RouteSelector {

    @Override
    public Route getRoute(final JanggiSide side, List<Route> routes, JanggiPosition origin,
                          JanggiPosition destination) {
        throw new PieceNotExistException();
    }
}
