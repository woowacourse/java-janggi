package domain.piece;

import domain.Position;

public interface PieceProvider {
    boolean isBlank(Position position);
    Piece getPiece(Position position);
}
