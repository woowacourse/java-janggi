package domain.board;

import domain.Position;
import domain.Side;
import domain.piece.Piece;
import domain.strategy.Direction;
import java.util.List;

public interface BoardReader {
    boolean isEmpty(Position position);
    boolean isAlly(Position position, Side side);
    Piece getPiece(Position position);
    List<Direction> getPalaceDiagonals(Position position);
    boolean isInsidePalace(Position position);
    boolean isInsidePalace(Position position, Side side);
}
