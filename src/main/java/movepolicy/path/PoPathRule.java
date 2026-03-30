package movepolicy.path;

import java.util.List;
import pieces.Piece;

public class PoPathRule implements PathRule {

    private static final int PATH_PIECES_SIZE_THRESHOLD = 1;

    @Override
    public void validatePathPieces(List<Piece> pathPieces) {
        List<Piece> pathFullPieces = pathPieces.stream()
                .filter(piece -> !piece.isEmpty())
                .toList();
        validateFullPieces(pathFullPieces);
    }

    private void validateFullPieces(List<Piece> pathFullPieces) {
        if (pathFullPieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 존재해야 합니다.");
        }
        if (pathFullPieces.size() != PATH_PIECES_SIZE_THRESHOLD) {
            throw new IllegalArgumentException("이동 경로엔 기물이 1개만 존재해야 합니다.");
        }
        if (hasPo(pathFullPieces)) {
            throw new IllegalArgumentException("포는 포를 뛰어 넘을 수 없습니다.");
        }
    }

    private static boolean hasPo(List<Piece> pathPieces) {
        return pathPieces.getFirst().isPo();
    }
}
