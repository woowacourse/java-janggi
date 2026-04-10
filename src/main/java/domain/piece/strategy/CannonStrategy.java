package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;

public class CannonStrategy implements MoveStrategy {
    @Override
    public void validateMove(Position from, Position to, BoardChecker boardChecker) {
        List<Position> path = boardChecker.findMovePath(from, to)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 포의 이동 방향이 올바르지 않습니다."));

        List<Piece> piecesInPath = boardChecker.findPiecesInPath(path);
        boolean targetPieceType = boardChecker.isTargetType(to, PieceType.CANNON);

        validateHasNeck(piecesInPath);
        validateNoCannonInPath(piecesInPath);
        validateOnlyOneNeck(piecesInPath);
        validateCannonOnDestination(targetPieceType);
    }

    private void validateHasNeck(List<Piece> piecesInPath) {
        if (piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 포는 이동 경로 상의 기물을 하나 넘어야만 움직일 수 있습니다.");
        }
    }

    private void validateOnlyOneNeck(List<Piece> piecesInPath) {
        if (piecesInPath.size() > 1) {
            throw new IllegalArgumentException("[ERROR] 포 이동 경로 상에 기물이 두 개 이상 있어 움직일 수 없습니다.");
        }
    }

    private void validateNoCannonInPath(List<Piece> piecesInPath) {
        boolean hasCannon = piecesInPath
                .stream()
                .anyMatch(piece -> piece.type().equals(PieceType.CANNON));

        if (hasCannon) {
            throw new IllegalArgumentException("[ERROR] 포 이동 경로 상에 포가 존재하여 움직일 수 없습니다.");
        }
    }

    private void validateCannonOnDestination(boolean targetPieceType) {
        if (targetPieceType) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }
}
