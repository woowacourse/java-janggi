package movepolicy.path;

import java.util.List;
import pieces.Piece;

public class EmptyPathRule implements PathRule {

    @Override
    public boolean validatePathPieces(List<Piece> pathPieces) {
        if (!pathPieces.isEmpty()) {
            throw new IllegalArgumentException("이동 경로엔 기물이 있을 수 없습니다.");
        }
        return true;
    }
}
