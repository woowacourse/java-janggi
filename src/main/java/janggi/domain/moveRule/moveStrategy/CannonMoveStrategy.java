package janggi.domain.moveRule.moveStrategy;

import janggi.domain.piece.Piece;
import java.util.List;

public class CannonMoveStrategy implements moveStrategy {
    private static final CannonMoveStrategy INSTANCE = new CannonMoveStrategy();

    private CannonMoveStrategy() {}

    public static CannonMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        int pieceCount = piece.countPieceInRoute(piecesInRoute);
        boolean noSamePieceOnRoute = piecesInRoute.stream().noneMatch(piece::isSamePieceType);
        boolean notTakeSamePiece = !piece.isSamePieceType(destination);
        boolean isOtherTeam = piece.isOtherTeam(destination);
        return pieceCount == 1 && isOtherTeam && noSamePieceOnRoute && notTakeSamePiece;
    }
}
