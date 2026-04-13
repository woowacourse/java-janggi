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
    private static final String INVALID_CANNON_POSITION_IN_PALACE_ERROR_MESSAGE =
            "[ERROR] 포는 궁성 내부에서 대각선 이동은 점대칭만 가능합니다.";

    @Override
    public void move(Position from, Position to, PathChecker pathChecker) {
        validatePalaceJumpShape(from, to, pathChecker);

        List<Position> path = determinePath(from, to, pathChecker);

        List<Piece> piecesInPath = pathChecker.findPiecesInPath(path);
        boolean targetPieceType = pathChecker.isTargetType(to, PieceType.CANNON);
        validateCannonMove(piecesInPath, targetPieceType);
    }

    private void validateCannonMove(List<Piece> piecesInPath, boolean targetPieceType) {
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

    private void validateCannonOnDestination(boolean targetPieceType) {
        if (targetPieceType) {
            throw new IllegalArgumentException(CANNON_CANNOT_CAPTURE_CANNON_ERROR_MESSAGE);
        }
    }

    private void validatePalaceJumpShape(Position from, Position to, PathChecker pathChecker) {
        if (pathChecker.isInDifferencePalace(from, to)) {
            return;
        }
        if (pathChecker.isInPalace(from) && pathChecker.isInPalace(to)) {
            checkValidDistanceInPalace(from, to);
        }
    }

    private void checkValidDistanceInPalace(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());
        boolean isDiagonalJump = dx == 2 && dy == 2;
        boolean isLinearJump = (dx == 2 && dy == 0) || (dx == 0 && dy == 2);

        if (!isDiagonalJump && !isLinearJump) {
            throw new IllegalArgumentException(INVALID_CANNON_POSITION_IN_PALACE_ERROR_MESSAGE);
        }
    }

    private List<Position> determinePath(Position from, Position to, PathChecker pathChecker) {
        if (isDiagonalJumpInPalace(from, to, pathChecker)) {
            return List.of(pathChecker.findPalaceCenter(from));
        }
        return from.findPath(to);
    }

    private boolean isDiagonalJumpInPalace(Position from, Position to, PathChecker pathChecker) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        return pathChecker.isInPalace(from) && pathChecker.isInPalace(to) && dx == 2 && dy == 2;
    }
}
