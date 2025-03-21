package janggi.domain.moveRule;

import janggi.domain.piece.Piece;
import java.util.List;

public class CannonMoveRule implements MoveRule {
    private static final CannonMoveRule CANNON_MOVE_RULE = new CannonMoveRule();

    private CannonMoveRule() {

    }

    public static CannonMoveRule getRule() {
        return CANNON_MOVE_RULE;
    }

    @Override
    public boolean canMove(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        int pieceCount = piece.countPieceInRoute(piecesInRoute);
        boolean noSamePieceOnRoute = piecesInRoute.stream().noneMatch(piece::isSamePieceType);
        boolean notTakeSamePiece = !piece.isSamePieceType(destination);
        boolean isOtherTeam = piece.isOtherTeam(destination);
        return pieceCount == 1 && isOtherTeam && noSamePieceOnRoute && notTakeSamePiece;
    }
}
