package movepolicy;

import pieces.Piece;
import position.Position;

public record MoveHistory(
    Position departure,
    Position destination,
    Piece movingPiece,
    Piece capturedPiece
) {

    public boolean isCaptured() {
        return capturedPiece == null;
    }
}
