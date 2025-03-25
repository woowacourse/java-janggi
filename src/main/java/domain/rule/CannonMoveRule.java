package domain.rule;

import domain.piece.Piece;
import java.util.List;

public class CannonMoveRule implements MoveRule {

    private static final int CANNON_OBSTACLE_COUNT = 1;
    private static final CannonMoveRule INSTANCE = new CannonMoveRule();

    public static CannonMoveRule getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidateMoveRule(Piece piece, Piece destination, List<Piece> piecesInRoute) {
        int pieceCountInRoute = piece.countObstacles(piecesInRoute);
        if (pieceCountInRoute != CANNON_OBSTACLE_COUNT) {
            return false;
        }

        boolean hasSamePieceType = piece.hasSamePieceType(piecesInRoute);
        if (hasSamePieceType) {
            return false;
        }

        boolean isDestinationSamePiece = piece.isSamePieceType(destination);
        if (isDestinationSamePiece) {
            return false;
        }

        return piece.isOtherTeam(destination);
    }
}
