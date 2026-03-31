package domain.movepolicy.path;

import java.util.List;
import domain.pieces.Piece;

public interface PathRule {

    void validatePathPieces(List<Piece> pathPieces);
}
