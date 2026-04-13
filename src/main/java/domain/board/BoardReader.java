package domain.board;

import domain.game.Position;
import domain.piece.Piece;

public interface BoardReader {
    boolean isEmpty(Position position);
    Piece getPiece(Position position);
}
