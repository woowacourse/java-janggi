package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.vo.position.Position;

public interface BoardView {
    Piece findByPosition(Position position);

    boolean isEmptyPosition(Position position);

    PieceType findTypeByPosition(Position position);
}
