package janggi.domain.moveRule.routeStrategy;

import janggi.domain.piece.Piece;
import java.util.List;

public class DefaultRouteStrategy implements RouteStrategy {
    public boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        return piece.isOtherTeam(destination) && piece.countPieceInRoute(piecesInRoute) == 0;
    }
}
