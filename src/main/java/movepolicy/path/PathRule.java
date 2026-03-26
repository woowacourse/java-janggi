package movepolicy.path;

import java.util.List;
import pieces.Piece;

public interface PathRule {
    boolean validatePathPieces(List<Piece> pathPieces);
}
