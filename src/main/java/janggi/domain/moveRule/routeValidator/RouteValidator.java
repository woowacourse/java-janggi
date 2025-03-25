package janggi.domain.moveRule.routeValidator;

import janggi.domain.piece.Piece;
import java.util.List;

public interface RouteValidator {
    boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute);
}
