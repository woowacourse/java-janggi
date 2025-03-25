package janggi.domain.moveRule.routeValidator;

import janggi.domain.piece.Piece;
import java.util.List;

public class DefaultRouteValidator implements RouteValidator {
    private static final DefaultRouteValidator INSTANCE = new DefaultRouteValidator();

    private DefaultRouteValidator() {}

    public static DefaultRouteValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        return piece.isOtherTeam(destination) && piece.countPieceInRoute(piecesInRoute) == 0;
    }
}
