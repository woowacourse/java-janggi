package domain.board;

import domain.common.Position;
import domain.piece.Piece;

public interface BoardReader {
    boolean isEmpty(Position position);
    Piece getPiece(Position position);
}
