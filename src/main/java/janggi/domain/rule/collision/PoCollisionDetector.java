package janggi.domain.rule.collision;

import static janggi.exception.ErrorCode.DESTINATION_OCCUPIED_SAME_TEAM_ERROR;
import static janggi.exception.ErrorCode.PO_EXISTENCE_IN_PATH_ERROR;
import static janggi.exception.ErrorCode.PO_REQUIRED_SCREEN_COUNT_ERROR;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.exception.PieceOnPathException;
import java.util.List;

public class PoCollisionDetector implements CollisionDetector {

    private static final PoCollisionDetector INSTANCE = new PoCollisionDetector();
    private static final int REQUIRED_SCREEN_COUNT = 1;

    private PoCollisionDetector() {
    }

    public static PoCollisionDetector getInstance() {
        return INSTANCE;
    }

    @Override
    public void check(Piece piece, List<Piece> piecesOnPath) {
        validateOneObstacle(piecesOnPath);
        validatePoExistence(piecesOnPath);
        validateDestination(piece, piecesOnPath);
    }

    private void validateOneObstacle(List<Piece> piecesOnPath) {
        List<Piece> middlePath = piecesOnPath.subList(0, piecesOnPath.size() - 1);

        long obstacleCount = middlePath.stream()
                .filter(piece -> !piece.isEmpty())
                .count();

        if (obstacleCount != REQUIRED_SCREEN_COUNT) {
            throw new PieceOnPathException(PO_REQUIRED_SCREEN_COUNT_ERROR, obstacleCount);
        }
    }

    private void validatePoExistence(List<Piece> piecesOnPath) {
        boolean hasPo = piecesOnPath.stream()
                .anyMatch(piece -> piece.isSame(PieceType.PO));

        if (hasPo) {
            throw new PieceOnPathException(PO_EXISTENCE_IN_PATH_ERROR);
        }
    }

    private void validateDestination(Piece piece, List<Piece> piecesOnPath) {
        Piece destinationPiece = piecesOnPath.getLast();
        if (destinationPiece.isSameSide(piece)) {
            throw new PieceOnPathException(DESTINATION_OCCUPIED_SAME_TEAM_ERROR, destinationPiece);
        }
    }
}
