package janggi.domain.moveRule.routeStrategy;

import janggi.domain.piece.Piece;
import java.util.List;

public interface RouteStrategy {
    boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute);
}
