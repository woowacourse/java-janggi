package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

public interface BoardMediator {
    boolean hasPieceAt(Position position);

    Piece getPieceInPosition(Position position);
}
