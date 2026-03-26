package domain;

import java.util.List;

public interface PathRule {
    boolean validatePathPieces(List<Piece> pathPieces);
}
