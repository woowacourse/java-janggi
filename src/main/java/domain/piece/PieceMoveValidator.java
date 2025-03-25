package domain.piece;

import domain.spatial.Position;
import java.util.List;

public class PieceMoveValidator {

    public void validateTeamPieceInTargetPosition(final Pieces pieces, final Position targetPosition) {
        if (pieces.existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }

    public void validateMovePath(final Piece movePiece, final Position targetPosition, final List<Pieces> allPieces) {
        List<Position> path = movePiece.getPath(targetPosition);

        int pathPieceCount = allPieces.stream()
                .mapToInt(pieces -> pieces.countPiecesInPositions(path))
                .sum();

        movePiece.validateMoveByPathPieceCount(pathPieceCount);
    }

    public void validateCannonCapture(final Pieces oppositePieces, final Position targetPosition) {
        if (oppositePieces.isCannonByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 포는 상대 포를 잡을 수 없습니다.");
        }
    }

    public void validateCannonPath(final List<Position> paths, final List<Pieces> allPieces) {
        if (paths.stream().anyMatch(path -> existsCannon(allPieces, path))) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 포를 지나칠 수 없습니다.");
        }
    }

    private boolean existsCannon(final List<Pieces> allPieces, final Position position) {
        return allPieces.stream()
                .anyMatch(pieces -> pieces.isCannonByPosition(position));
    }
}
