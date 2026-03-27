package movepolicy.path;

import java.util.List;
import pieces.Piece;

public interface PathRule {
    void validatePathPieces(List<Piece> pathPieces);
}
