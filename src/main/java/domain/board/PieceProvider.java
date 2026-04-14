package domain.board;

import domain.piece.Piece;
import domain.position.Position;

public interface PieceProvider {
    Piece getPiece(Position position);
}
