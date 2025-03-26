package domain;

import domain.piece.Piece;
import domain.spatial.Position;
import java.util.Map;
import java.util.Objects;

public class MoveInfo {

    private final Map<Position, Piece> pathPieces;

    public MoveInfo(final Map<Position, Piece> pathPieces) {
        this.pathPieces = pathPieces;
    }

    public boolean isPathInPiece() {
        return pathPieces.values().stream()
                .anyMatch(Objects::isNull);
    }
}
