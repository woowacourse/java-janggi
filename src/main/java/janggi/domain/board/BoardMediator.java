package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

public interface BoardMediator {
    boolean existsInPosition(Position position);
    Piece getPieceInPosition(Position position);

}
