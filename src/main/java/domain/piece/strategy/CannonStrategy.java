package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;

public class CannonStrategy implements MoveStrategy {
    private static final String CANNON_NEEDS_BRIDGE_ERROR_MESSAGE =
            "[ERROR] 포는 이동 경로 상의 기물을 하나 넘어야만 움직일 수 있습니다.";
    private static final String CANNON_CANNOT_MOVE_OVER_MULTIPLE_PIECES_ERROR_MESSAGE =
            "[ERROR] 포 이동 경로 상에 기물이 두 개 이상 있어 움직일 수 없습니다.";
    private static final String ANOTHER_CANNON_IS_EXISTS_ON_PATH_ERROR_MESSAGE =
            "[ERROR] 포 이동 경로 상에 포가 존재하여 움직일 수 없습니다.";
    private static final String CANNON_CANNOT_CAPTURE_CANNON_ERROR_MESSAGE =
            "[ERROR] 포는 포를 잡을 수 없습니다.";

    @Override
    public void move(Position from, Position to, BoardChecker boardChecker) {
        List<Position> path = from.findPath(to);
        List<Piece> piecesInPath = boardChecker.findPiecesInPath(path);
        boolean targetPieceType = boardChecker.isTargetType(to, PieceType.CANNON);

        validateHasNeck(piecesInPath);
        validateNoCannonInPath(piecesInPath);
        validateOnlyOneNeck(piecesInPath);
        validateCannonOnDestination(targetPieceType);
    }

    private void validateHasNeck(List<Piece> piecesInPath) {
        if (piecesInPath.isEmpty()) {
            throw new IllegalArgumentException(CANNON_NEEDS_BRIDGE_ERROR_MESSAGE);
        }
    }

    private void validateOnlyOneNeck(List<Piece> piecesInPath) {
        if (piecesInPath.size() > 1) {
            throw new IllegalArgumentException(CANNON_CANNOT_MOVE_OVER_MULTIPLE_PIECES_ERROR_MESSAGE);
        }
    }

    private void validateNoCannonInPath(List<Piece> piecesInPath) {
        boolean hasCannon = piecesInPath
                .stream()
                .anyMatch(piece -> piece.type().equals(PieceType.CANNON));

        if (hasCannon) {
            throw new IllegalArgumentException(ANOTHER_CANNON_IS_EXISTS_ON_PATH_ERROR_MESSAGE);
        }
    }

    private void validateCannonOnDestination(boolean targetPieceType) {
        if (targetPieceType) {
            throw new IllegalArgumentException(CANNON_CANNOT_CAPTURE_CANNON_ERROR_MESSAGE);
        }
    }
}
