package domain.board;

import domain.Position;
import domain.Side;
import domain.piece.Piece;

public interface BoardReader {
    boolean isEmpty(Position position);
    boolean isAlly(Position position, Side side);
    Piece getPiece(Position position);
}
