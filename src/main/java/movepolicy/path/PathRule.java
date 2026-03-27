package movepolicy.path;

import java.util.List;
import pieces.FullPiece;

public interface PathRule {
    void validatePathPieces(List<FullPiece> pathPieces);
}
