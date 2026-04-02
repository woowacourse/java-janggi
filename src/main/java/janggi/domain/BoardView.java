package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.vo.Position;

public interface BoardView {

    Piece findByPosition(Position position);

    boolean isEmptyPosition(Position position);

    boolean isInsidePalace(Position position);

    boolean canMoveDiagonallyInPalace(Position from, Position to);

    boolean isDiagonalInPalace(Position from, Position to);

    Position getDiagonalMidpointInPalace(Position from, Position to);

}
