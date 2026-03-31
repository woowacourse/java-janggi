package domain.movepolicy.path;

import java.util.List;
import domain.pieces.FullPiece;
import domain.pieces.Piece;

public class EmptyPathRule implements PathRule {

    @Override
    public void validatePathPieces(List<Piece> pathPieces) {
        List<FullPiece> pathFullPieces = pathPieces.stream()
            .filter(piece -> !piece.isEmpty())
            .map(piece -> (FullPiece) piece)
            .toList();
        validateFullPieces(pathFullPieces);
    }

    private void validateFullPieces(List<FullPiece> pathFullPieces) {
        if (!pathFullPieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 있을 수 없습니다.");
        }
    }
}
