package janggi.domain.routePolicy;

import janggi.domain.piece.Piece;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Set;

public interface RoutePolicy {
    Set<Route> getPossibleRoutes(final Piece piece, final List<Piece> pieces);
}
