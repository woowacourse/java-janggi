package janggi.domain.rule.collision;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
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
            throw new IllegalArgumentException("포는 반드시 하나의 기물을 넘어야 합니다.");
        }
    }

    private void validatePoExistence(List<Piece> piecesOnPath) {
        boolean hasPo = piecesOnPath.stream()
                .anyMatch(piece -> piece.getType() == PieceType.PO);

        if (hasPo) {
            throw new IllegalArgumentException("이동 경로 또는 도착지에 포가 존재할 수 없습니다.");
        }
    }

    private void validateDestination(Piece piece, List<Piece> piecesOnPath) {
        Piece destinationPiece = piecesOnPath.getLast();
        if (destinationPiece.isSameSide(piece)) {
            throw new IllegalArgumentException("도착 위치에 같은 팀이 존재합니다");
        }
    }
}
