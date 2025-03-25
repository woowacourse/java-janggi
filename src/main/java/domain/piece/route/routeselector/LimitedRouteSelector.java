package domain.piece.route.routeselector;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class LimitedRouteSelector implements JanggiPieceRouteSelector {

    @Override
    public List<MovingPattern> getRoute(final JanggiSide side, final List<List<MovingPattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        return routes.stream()
                .filter(route -> {
                    if (beforePosition.canMove(route)) {
                        JanggiPosition newPosition = beforePosition.move(route);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(InvalidPathException::new);
    }
}
