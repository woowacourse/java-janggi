package janggi.domain.routePolicy;

import janggi.domain.piece.Piece;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovePolicyForNormal implements MovePolicy {
    public Set<Route> getPossibleRoutes(Piece piece, List<Piece> pieces) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidNormalRoute(route, piece, pieces))
                .collect(Collectors.toSet());
    }

    private boolean isValidNormalRoute(Route route, Piece piece, List<Piece> pieces) {
        return pieces.stream()
                .filter(route::hasPosition)
                .allMatch(currentPiece -> route.isDestination(currentPiece) && piece.isEnemy(currentPiece));
    }
}
