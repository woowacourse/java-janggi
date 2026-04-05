package janggi.domain.rule.collision;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.List;

@SuppressWarnings("java:S6548")
public class DefaultCollisionDetector implements CollisionDetector {

    private static final DefaultCollisionDetector INSTANCE = new DefaultCollisionDetector();

    private DefaultCollisionDetector() {
    }

    public static DefaultCollisionDetector getInstance() {
        return INSTANCE;
    }

    @Override
    public void check(Side side, List<Piece> piecesOnPath) {
        validateMiddlePath(piecesOnPath);
        validateDestination(side, piecesOnPath);
    }

    private void validateMiddlePath(List<Piece> piecesOnPath) {
        List<Piece> middlePath = piecesOnPath.subList(0, piecesOnPath.size() - 1);
        for (Piece pathPiece : middlePath) {
            validateNoObstacle(pathPiece);
        }
    }

    private void validateNoObstacle(Piece pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재합니다");
        }
    }

    private void validateDestination(Side side, List<Piece> piecesOnPath) {
        Piece destinationPiece = piecesOnPath.getLast();
        if (destinationPiece.isEmpty()) {
            return;
        }
        if (destinationPiece.isSameSide(side)) {
            throw new IllegalArgumentException("도착 위치에 같은 팀이 존재합니다");
        }
    }
}
