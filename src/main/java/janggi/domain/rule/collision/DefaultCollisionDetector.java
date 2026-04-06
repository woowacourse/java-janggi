package janggi.domain.rule.collision;

import janggi.domain.piece.Piece;
import janggi.exception.ErrorCode;
import janggi.exception.PieceOnPathException;
import java.util.List;

public class DefaultCollisionDetector implements CollisionDetector {

    private static final DefaultCollisionDetector INSTANCE = new DefaultCollisionDetector();

    private DefaultCollisionDetector() {
    }

    public static DefaultCollisionDetector getInstance() {
        return INSTANCE;
    }

    @Override
    public void check(Piece piece, List<Piece> piecesOnPath) {
        validateMiddlePath(piecesOnPath);
        validateDestination(piece, piecesOnPath);
    }

    private void validateMiddlePath(List<Piece> piecesOnPath) {
        List<Piece> middlePath = piecesOnPath.subList(0, piecesOnPath.size() - 1);
        for (Piece pathPiece : middlePath) {
            if (pathPiece.isNotEmpty()) {
                throw new PieceOnPathException(ErrorCode.COLLISION_DETECT_ERROR, pathPiece);
            }
        }
    }

    private void validateDestination(Piece piece, List<Piece> piecesOnPath) {
        Piece destinationPiece = piecesOnPath.getLast();
        if (destinationPiece.isSameSide(piece)) {
            throw new PieceOnPathException(ErrorCode.DESTINATION_OCCUPIED_SAME_TEAM_ERROR, destinationPiece);
        }
    }
}
