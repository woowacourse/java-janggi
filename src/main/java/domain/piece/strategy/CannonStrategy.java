package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;

public class CannonStrategy implements MoveStrategy {
    private static final String CANNON_CANNOT_MOVE_ERROR_MESSAGE =
            "[ERROR] 포 이동 경로 상에 기물이 한 개를 초과하여 움직일 수 없습니다.";
    private static final String ANOTHER_CANNON_IS_EXISTS_ON_PATH_ERROR_MESSAGE =
            "[ERROR] 포 이동 경로 상에 포가 존재하여 움직일 수 없습니다.";
    private static final String CANNON_CANNOT_CAPTURE_CANNON_ERROR_MESSAGE =
            "[ERROR] 포는 포를 잡을 수 없습니다.";

    @Override
    public void move(Position from, Position to, PathChecker pathChecker) {
        List<Position> path = from.findPath(to);
        List<Piece> piecesInPath = pathChecker.findPiecesInPath(path);
        PieceType targetPieceType = pathChecker.findPieceType(to);

        validateOnePiece(piecesInPath);
        validateNoCannonInPath(piecesInPath);
        validateCannonOnDestination(targetPieceType);
    }

    private void validateOnePiece(List<Piece> piecesInPath) {
        if (piecesInPath.size() != 1) {
            throw new IllegalArgumentException(CANNON_CANNOT_MOVE_ERROR_MESSAGE);
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

    private void validateCannonOnDestination(PieceType targetPieceType) {
        if (targetPieceType != null && targetPieceType.equals(PieceType.CANNON)) {
            throw new IllegalArgumentException(CANNON_CANNOT_CAPTURE_CANNON_ERROR_MESSAGE);
        }
    }
}
