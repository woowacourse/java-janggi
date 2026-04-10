package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

public interface BoardState {
    boolean hasPieceAt(Position position);
    Piece getPieceAt(Position position);
}
