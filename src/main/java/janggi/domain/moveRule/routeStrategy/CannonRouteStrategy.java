package janggi.domain.moveRule.routeStrategy;

import janggi.domain.piece.Piece;
import java.util.List;

public class CannonRouteStrategy implements RouteStrategy {
    @Override
    public boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        long pieceCount = piece.countPieceInRoute(piecesInRoute);
        boolean noSamePieceOnRoute = piecesInRoute.stream().noneMatch(piece::isSamePieceType);
        boolean notTakeSamePiece = !piece.isSamePieceType(destination);
        boolean isOtherTeam = piece.isOtherTeam(destination);
        return pieceCount == 1 && isOtherTeam && noSamePieceOnRoute && notTakeSamePiece;
    }
}
