package movepolicy.path;

import java.util.List;
import pieces.Piece;

public class PoPathRule implements PathRule {
    private static final int PATH_PIECES_SIZE_THRESHOLD = 1;

    @Override
    public boolean validatePathPieces(List<Piece> pathPieces) {
        if (pathPieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 존재해야 합니다.");
        }
        if (pathPieces.size() != PATH_PIECES_SIZE_THRESHOLD) {
            throw new IllegalArgumentException("이동 경로엔 기물이 1개만 존재해야 합니다.");
        }
        if (isPo(pathPieces)) {
            throw new IllegalArgumentException("포는 포를 뛰어넘을 수 없습니다.");
        }
        return true;
    }

    private static boolean isPo(List<Piece> pathPieces) {
        return pathPieces.getFirst().isPo();
    }
}
