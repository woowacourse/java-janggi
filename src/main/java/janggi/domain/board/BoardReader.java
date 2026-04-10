package janggi.domain.board;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.space.Direction;
import janggi.domain.space.Position;
import java.util.List;

public interface BoardReader {
    boolean isEmpty(Position position);
    boolean isAlly(Position position, Side side);
    Piece getPiece(Position position);
    List<Direction> getPalaceDiagonals(Position position);
    boolean isInsidePalace(Position position);
    boolean isInsidePalace(Position position, Side side);
}
