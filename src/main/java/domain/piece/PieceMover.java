package domain.piece;

import domain.spatial.Position;
import java.util.List;

public class PieceMover {

    private final Pieces playerPieces;
    private final Pieces opponentPieces;

    public PieceMover(final Pieces playerPieces, final Pieces opponentPieces) {
        this.playerPieces = playerPieces;
        this.opponentPieces = opponentPieces;
    }

    public void movePiece(final Position startPosition, final Position targetPosition) {
        List<Position> paths = playerPieces.getPiecePaths(startPosition, targetPosition);
        int pathPieceCount = calculatePathPieceCount(paths);

        playerPieces.validateMovePath(startPosition, pathPieceCount);
        validateTargetPosition(targetPosition);

        if (playerPieces.isCannonByPosition(startPosition)) {
            validateCannonMove(targetPosition, paths);
        }

        playerPieces.updatePosition(startPosition, targetPosition);
    }

    private int calculatePathPieceCount(final List<Position> paths) {
        return playerPieces.countPiecesInPositions(paths) + opponentPieces.countPiecesInPositions(paths);
    }

    private void validateTargetPosition(final Position targetPosition) {
        PieceMoveValidator moveValidator = new PieceMoveValidator(playerPieces);
        moveValidator.validateTeamPieceInTargetPosition(targetPosition);
    }

    private void validateCannonMove(final Position targetPosition, final List<Position> paths) {
        CannonMoveValidator moveValidator = new CannonMoveValidator(playerPieces, opponentPieces);
        moveValidator.validateCannonCapture(targetPosition);
        moveValidator.validateCannonPath(paths);
    }
}
