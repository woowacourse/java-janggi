package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.vo.Position;

public interface BoardView {

    Piece findByPosition(Position position);

    boolean isEmptyPosition(Position position);

}
