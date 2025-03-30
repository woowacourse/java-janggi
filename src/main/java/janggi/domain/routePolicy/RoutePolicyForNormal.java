package janggi.domain.routePolicy;

import janggi.domain.piece.Piece;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoutePolicyForNormal implements RoutePolicy {
    @Override
    public Set<Route> getPossibleRoutes(final Piece piece, final List<Piece> pieces) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidNormalRoute(route, piece, pieces))
                .collect(Collectors.toSet());
    }

    private boolean isValidNormalRoute(final Route route, final Piece piece, final List<Piece> pieces) {
        return pieces.stream()
                .filter(route::hasPosition)
                .allMatch(currentPiece -> route.isDestination(currentPiece) && piece.isEnemy(currentPiece));
    }
}
