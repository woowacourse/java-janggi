package janggi.domain.rule.collision;

import janggi.domain.piece.Piece;
import java.util.List;

public class DefaultCollisionDetector implements CollisionDetector {

    @Override
    public void check(Piece piece, List<Piece> piecesOnPath) {
        validateMiddlePath(piecesOnPath);
        validateDestination(piece, piecesOnPath);
    }

    private void validateMiddlePath(List<Piece> piecesOnPath) {
        List<Piece> middlePath = piecesOnPath.subList(0, piecesOnPath.size() - 1);
        for (Piece pathPiece : middlePath) {
            if (!pathPiece.isEmpty()) {
                throw new IllegalArgumentException("이동 경로에 기물이 존재합니다");
            }
        }
    }

    private void validateDestination(Piece piece, List<Piece> piecesOnPath) {
        Piece destinationPiece = piecesOnPath.getLast();
        if (destinationPiece.isSameSide(piece)) {
            throw new IllegalArgumentException("도착 위치에 같은 팀이 존재합니다");
        }
    }
}
