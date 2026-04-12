package domain.board;

import domain.piece.Piece;
import domain.position.Position;

public interface PieceProvider {
    boolean isBlank(Position position);
    boolean isCannon(Position position);
    Piece getPiece(Position position);
}
