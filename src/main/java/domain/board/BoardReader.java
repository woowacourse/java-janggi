package domain.board;

import domain.piece.Piece;
import domain.Position;

public interface BoardReader {
    boolean isEmpty(Position position);
    Piece getPiece(Position position);
}
