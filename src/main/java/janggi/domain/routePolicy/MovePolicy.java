package janggi.domain.routePolicy;

import janggi.domain.piece.Piece;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Set;

public interface MovePolicy {

    Set<Route> getPossibleRoutes(Piece piece, List<Piece> pieces);

}
